package gameObjects;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Tyler on 3/29/2017.
 */
public class Game {
    Player player1 = new Player();
    Player player2 = new Player();
    Player player3 = new Player();
    Player player4 = new Player();
    Dice dice = new Dice();
    boolean endGame;
    Stage stage;
    private double width;
    private double height;
    private Game game;
    private PlayerScreen playerScreen;
    private TitleScreen title;
    private GameScreen gameScreen;
    String gameStage;
    final String SETUP = "SETUP";
    final String MAIN_GAME= "MAIN GAME";
    final String END_GAME = "END GAME";
    final String BANDIT_UPDATE = "BANDIT UPDATE";
    final String ROAD_BUILDING = "ROAD BUILDING";
    int remainingRoadBuildingRoads;
    String setUpStage;
    final String SETTLEMENT = "SETTLEMENT";
    final String ROAD = "ROAD";
    Player currentPlayer;
    int numPlayers;
    int numCurrentPlayer;
    int setUpRound;
    City setUpCity;
    ArrayList<Player> playerList = new ArrayList<>();
    ArrayList<String> deck;
    int previousLargestArmy;
    int playerLargestArmy;
    final String  CONFIRMED = "CONFIRMED";

    public Game(Stage primaryStage) {
        stage = primaryStage;
        initGameVariables();
//        stage.getScene().getRoot().setStyle("-fx-background-color: red");
        stage.getScene().setRoot(title);
        setTitleMouseListeners();




    }





    public void initGameVariables(){
        setUpStage = SETTLEMENT;
        gameStage = SETUP;
        game = this;
        Scene scene = stage.getScene();
        width = scene.getWidth();
        height = scene.getHeight();
        currentPlayer = player1;
        numPlayers = 4;
        numCurrentPlayer=1;
        setUpRound = 1;
        player1.setPlayerColor(Color.BLUE);
        player1.setPlayerIcon(new Image("images/DarkBluePlayer.png"));
        player1.setPlayerSettlement(new Image("images/DarkBlueSettlement.png"));
        player1.setPlayerCity(new Image("images/DarkBlueCity1.png"));

        player2.setPlayerColor(Color.RED);
        player2.setPlayerIcon(new Image("images/RedPlayer.png"));
        player2.setPlayerSettlement(new Image("images/RedSettlement.png"));
        player2.setPlayerCity(new Image("images/RedCity.png"));

        player3.setPlayerColor(Color.YELLOW);
        player3.setPlayerIcon(new Image("images/YellowPlayer.png"));
        player3.setPlayerSettlement(new Image("images/YellowSettlement2.png"));
        player3.setPlayerCity(new Image("images/YellowCity.png"));

        player4.setPlayerColor(Color.GREEN);
        player4.setPlayerIcon(new Image("images/GreenPlayer.png"));
        player4.setPlayerSettlement(new Image("images/GreenSettlement.png"));
        player4.setPlayerCity(new Image("images/GreenCity.png"));

        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);



        title = new TitleScreen(game.getHeight(), game.getWidth());
        playerScreen = new PlayerScreen(game.getHeight(), game.getWidth());
        deck = new ArrayList<>();
        createDeck();
        previousLargestArmy=0;
        playerLargestArmy = 2;
        remainingRoadBuildingRoads = 0;





    }
    private void createDeck(){
        for (int i = 0; i<14; i++){
            deck.add("knight");
        }
        for (int i = 0; i<5; i++){
            deck.add("vpCard");
        }
        for (int i =0; i<2; i++){
            deck.add("monopoly");
            deck.add("roadBuilding");
            deck.add("yearOfPlenty");
        }

    }

    public double getWidth(){return width;}

    public double getHeight(){return height;}

    public void nextTurn(){
        updatePlayer();
        if (!currentPlayer.getPlayerPassword().equals("")) {
            loginDialog();
        }

        beginTurn(currentPlayer);

    }
    private void loginDialog(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setHeaderText("Enter Username and Password");
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));
        Label username = new Label(currentPlayer.getPlayerName());

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        grid.add(new Label("Username:"),0,0);
        grid.add(username,1,0);
        grid.add(new Label("Password:"),0,1);
        grid.add(password,1,1);
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> username.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType){
                return password.getText();
            }
            return null;
        });


        StringBuilder Password = new StringBuilder("");
        int i = 0;

        do {
            if (i>0){
                dialog.setHeaderText("Password Incorrect");

            }
            i++;

            Password.delete(0,Password.length());
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(usernamePassword -> {

                Password.append(usernamePassword);
            });
        } while (!Password.toString().equals(currentPlayer.getPlayerPassword()));
    }

    public void setTitleMouseListeners() {
        title.btnNewGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setPlayerMouseListeners();

                stage.getScene().setRoot(playerScreen);

            }
        });
    }

    public void setPlayerMouseListeners() {
        playerScreen.btnStartGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                player1.setPlayerName(playerScreen.txtPlayer1Name.getText().toString());
                if(player1.getPlayerName().equals("")){player1.setPlayerName("player1");}
                player2.setPlayerName(playerScreen.txtPlayer2Name.getText().toString());
                if(player2.getPlayerName().equals("")){player2.setPlayerName("player2");}
                player3.setPlayerName(playerScreen.txtPlayer3Name.getText().toString());
                if(player3.getPlayerName().equals("")){player3.setPlayerName("player3");}
                player4.setPlayerName(playerScreen.txtPlayer4Name.getText().toString());
                if(player4.getPlayerName().equals("")){player4.setPlayerName("player4");}

                player1.setPlayerPassword(playerScreen.txtPlayer1Password.getText().toString());
                player2.setPlayerPassword(playerScreen.txtPlayer2Password.getText().toString());
                player3.setPlayerPassword(playerScreen.txtPlayer3Password.getText().toString());
                player4.setPlayerPassword(playerScreen.txtPlayer4Password.getText().toString());
                gameScreen = new GameScreen(game.getHeight(), game.getWidth(), playerList);
                gameScreen.resourcePane.dice = dice;
                gameScreen.resourcePane.setDiceParameters();
                setGameMouseListeners();
                updateCityAvailability();
                stage.getScene().setRoot(gameScreen);
            }
        });
    }
    public void setGameMouseListeners() {
        gameScreen.endGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        gameScreen.playerPane.btnNextTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage.equals(MAIN_GAME)) {
                    updatePlayerVP();
                    nextTurn();
                }
            }
        });
        for(int i = 0; i < gameScreen.board.cityList.size(); i++ ){
            gameScreen.board.cityList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (gameStage.equals(SETUP)) {
                            setUpSettlementPurchase((City) event.getSource());


                        } else if (gameStage.equals(MAIN_GAME)) {
                            cityPurchase(currentPlayer, (City) event.getSource());

                        }
                    }

            });
        }

        for (int i = 0; i < gameScreen.board.roadList.size(); i++){
            gameScreen.board.roadList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (gameStage.equals(SETUP)) {
                        setUpRoadPurchase((Road) event.getSource());
                    } else if (gameStage.equals(MAIN_GAME)) {
                        roadPurchase(currentPlayer, (Road) event.getSource());
                    } else if (gameStage.equals(ROAD_BUILDING)){
                        roadPurchase(currentPlayer, (Road) event.getSource());
                        remainingRoadBuildingRoads--;
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Road Building Card");
                        alert.setContentText("You have "+remainingRoadBuildingRoads+" free roads remaining.");
                        if(remainingRoadBuildingRoads<1){
                            gameStage = MAIN_GAME;
                            updateCityAvailability();
                        }
                    }
                }
            });
        }

        for (int i = 0; i < gameScreen.board.hexTiles.size(); i++){
            gameScreen.board.hexTiles.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (gameStage.equals(BANDIT_UPDATE)){
                        moveBandit((HexTile)event.getSource());

                    }
                }
            });
        }
        gameScreen.resourcePane.deck.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                purchaseCard(currentPlayer);
            }
        });
        gameScreen.resourcePane.brick.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage.equals(MAIN_GAME)) {
                    tradeResource("brick");
                }
            }
        });
        gameScreen.resourcePane.sheep.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage.equals(MAIN_GAME)){
                    tradeResource("sheep");
                }
            }
        });
        gameScreen.resourcePane.ore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage.equals(MAIN_GAME)) {
                    tradeResource("ore");
                }
            }
        });
        gameScreen.resourcePane.wood.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage.equals(MAIN_GAME)){
                    tradeResource("wood");
                }
            }
        });
        gameScreen.resourcePane.wheat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage.equals(MAIN_GAME)) {
                    tradeResource("wheat");
                }
            }
        });
        gameScreen.resourcePane.knights.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playCard("knight");
            }
        });
        gameScreen.resourcePane.victoryPointCards.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playCard("vpCard");
            }
        });
        gameScreen.resourcePane.roadBuilding.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playCard("roadBuilding");
            }
        });
        gameScreen.resourcePane.monopoly.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playCard("monopoly");
            }
        });
        gameScreen.resourcePane.yearOfPlenty.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playCard("yearOfPlenty");
            }
        });


    }

    private void setUpSettlementPurchase(City city){
        if (!city.owned&&city.available) {
            if(setUpStage.equals(SETTLEMENT)) {
                setUpCity = city;
                city.setOwner(currentPlayer);
                ArrayList<Integer> cityInfo = new ArrayList<>();
                cityInfo.add(city.getCityId());
                cityInfo.add(city.getCitySize());
                currentPlayer.addPlayerCity(cityInfo);
                disableCities();

                updateRoadAvailability();


                setUpStage = ROAD;
            }
        }
    }

    private void setUpRoadPurchase(Road road){
        if (!road.owned&&road.available) {
            if(setUpStage.equals(ROAD)) {
                road.setOwner(currentPlayer);
                currentPlayer.addPlayerRoad(road);
                disableRoads();
                updateCityAvailability();

                switch(numCurrentPlayer){
                    case 1:
                        if(setUpRound ==1){
                            currentPlayer = player2;
                            numCurrentPlayer = 2;
                        } else {
                            setUpResources();
                            gameStage = MAIN_GAME;
                            beginTurn(currentPlayer);
                            updatePlayerVP();
                        }
                            break;
                    case 2:
                        if(setUpRound == 1){
                            currentPlayer = player3;
                            numCurrentPlayer = 3;
                        } else {
                            currentPlayer = player1;
                            numCurrentPlayer = 1;
                        }
                            break;
                    case 3:
                        if(setUpRound == 1){
                            currentPlayer = player4;
                            numCurrentPlayer =4;
                        } else {
                            currentPlayer = player2;
                            numCurrentPlayer = 2;
                        }
                            break;
                    case 4:
                        if(setUpRound == 1){
                            setUpRound = 2;


                        } else {
                            currentPlayer = player3;
                            numCurrentPlayer = 3;
                        }
                        break;
                }

                setUpStage=SETTLEMENT;
            }
        }

    }

    private void updateCityAvailability(){
        ArrayList<City> ownedCities = new ArrayList<>();
        for (int i = 0; i < gameScreen.board.cityList.size(); i++){
            if ((gameScreen.board.cityList.get(i).isOwned() && gameStage.equals(SETUP)) || (gameScreen.board.cityList.get(i).getOwner()==currentPlayer && gameStage.equals(MAIN_GAME))){
                ownedCities.add(gameScreen.board.cityList.get(i));
            } else {
                if (gameStage.equals(SETUP)) {
                    gameScreen.board.cityList.get(i).available = true;
                    gameScreen.board.cityList.get(i).clip.setFill(Color.AQUA);
                } else if (gameStage.equals(MAIN_GAME)){
                    for (int j = 0; j < gameScreen.board.cityList.get(i).connectedRoads.size(); j++){
                        if (gameScreen.board.cityList.get(i).connectedRoads.get(j).owner==currentPlayer){
                            gameScreen.board.cityList.get(i).available = true;
                            gameScreen.board.cityList.get(i).clip.setFill(Color.AQUA);
                        }
                    }
                }
            }
        }
        for (int i = 0; i <ownedCities.size(); i++){
            for (int k = 0; k < gameScreen.board.cityList.size(); k++){
                if (!gameScreen.board.cityList.get(k).isOwned()) {
                    for (int l = 0; l < ownedCities.get(i).connectedRoads.size(); l++) {
                        for (int m = 0; m < gameScreen.board.cityList.get(k).connectedRoads.size(); m++) {
                            if (gameScreen.board.cityList.get(k).connectedRoads.get(m) == ownedCities.get(i).connectedRoads.get(l)) {
                                gameScreen.board.cityList.get(k).available = false;
                                gameScreen.board.cityList.get(k).clip.setFill(Color.BLACK);
                            }
                        }
                    }
                }

            }
        }
    }

    private void updateRoadAvailability(){
        if (gameStage.equals(SETUP)){
            for (int i = 0; i < setUpCity.connectedRoads.size(); i++){
                for (int k = 0; k < gameScreen.board.roadList.size(); k++){
                    if(setUpCity.connectedRoads.get(i) == gameScreen.board.roadList.get(k)){
                        gameScreen.board.roadList.get(k).setFill(Color.AQUA);
                        gameScreen.board.roadList.get(k).available = true;
                    }
                }
            }

        } else if (gameStage.equals(MAIN_GAME)){
            for(int i =0; i <currentPlayer.playerRoads.size(); i++){
                for(int j=0; j < currentPlayer.playerRoads.get(i).connectedCities.size(); j++){
                    for (int k = 0; k < gameScreen.board.roadList.size(); k++){
                        for (int l = 0; l < gameScreen.board.roadList.get(k).connectedCities.size(); l++) {
                            if ((!gameScreen.board.roadList.get(k).isOwned())&&(gameScreen.board.roadList.get(k).connectedCities.get(l)==currentPlayer.playerRoads.get(i).connectedCities.get(j))){
                                gameScreen.board.roadList.get(k).setFill(Color.AQUA);
                                gameScreen.board.roadList.get(k).available = true;

                            }
                        }
                    }
                }
            }
        }



    }

    private void disableCities(){
        for(int i = 0; i<gameScreen.board.cityList.size(); i++){
            if(!gameScreen.board.cityList.get(i).isOwned()){
                gameScreen.board.cityList.get(i).clip.setFill(Color.BLACK);
                gameScreen.board.cityList.get(i).available = false;

            }
        }
    }

    private void disableRoads(){
        for(int i = 0; i<gameScreen.board.roadList.size(); i++){
            if(!gameScreen.board.roadList.get(i).isOwned()){
                gameScreen.board.roadList.get(i).setFill(Color.BLACK);
                gameScreen.board.roadList.get(i).available = false;
            }
        }
    }
    private void setUpResources(){
        for(int i = 0; i <gameScreen.board.hexTiles.size(); i++){
            HexTile hex = gameScreen.board.hexTiles.get(i);
            for(int k = 0; k <hex.connectedCities.size(); k++){
                City city = hex.connectedCities.get(k);

                if (city.isOwned()) {
                    city.getOwner().addPlayerResource(hex.hexType, 1);
                }
            }
        }
    }

    private void updateScreen(Player player){
        gameScreen.update(player);

    }

    private void beginTurn(Player player){
        disableCities();
        disableRoads();
        updateCityAvailability();
        updateRoadAvailability();
        gameScreen.resourcePane.updateDice();
        if(dice.getDiceResult()==7){
            discardHand();
            banditUpdate();
        } else {
            for (int i = 0; i < gameScreen.board.hexTiles.size(); i++) {
                HexTile hex = gameScreen.board.hexTiles.get(i);
                if (hex.getHexRoll().equals(dice.getDiceResult())) {
                    for (int k = 0; k < hex.connectedCities.size(); k++) {
                        for (int l = 0; l < gameScreen.board.cityList.size(); l++) {
                            if (hex.connectedCities.get(k) == gameScreen.board.cityList.get(l)) {
                                City city = gameScreen.board.cityList.get(l);
                                if (city.isOwned()) {
                                    city.owner.addPlayerResource(hex.hexType, city.getCitySize());
                                }
                            }
                        }
                    }
                }
            }
        }
        updateScreen(player);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Next Turn");
        alert.setHeaderText("It is " + currentPlayer.getPlayerName()+"'s turn.");
        alert.setContentText("The roll this turn was a "+Integer.toString(dice.getDiceResult()));
    }

    private void discardHand(){
        for (int i = 0; i<playerList.size(); i++){
            Player player = playerList.get(i);
            int handSize = player.playerHandSize;
            if (handSize>7){
                ArrayList<String> playerCards = new ArrayList<>();
                for(int k = 0; k < player.getPlayerWood(); k++){
                    playerCards.add("forest");
                }
                for(int k = 0; k < player.getPlayerSheep(); k++){
                    playerCards.add("pasture");
                }
                for(int k = 0; k < player.getPlayerWheat(); k++){
                    playerCards.add("field");
                }
                for(int k = 0; k < player.getPlayerBrick(); k++){
                    playerCards.add("hill");
                }
                for(int k = 0; k < player.getPlayerOre(); k++){
                    playerCards.add("mountain");
                }
                Random rand = new Random();
                for(int k = 0; k<handSize/2;k++){
                    String resource = playerCards.get(rand.nextInt(playerCards.size()-1));
                    player.addPlayerResource(resource,-1);
                }

            }

        }


    }

    private void banditUpdate(){
        //insert dialog box here
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Move Robber");
        alert.setContentText("Select a tile to move the robber to.");
        alert.showAndWait();
        gameStage = BANDIT_UPDATE;

    }

    private void moveBandit(HexTile hex){
        for (int i = 0; i < gameScreen.board.hexTiles.size(); i++){
            if (gameScreen.board.hexTiles.get(i).bandit){
                gameScreen.board.hexTiles.get(i).bandit = false;
                gameScreen.board.hexTiles.get(i).rollCircle.setFill(Color.WHITE);
                if (gameScreen.board.hexTiles.get(i).hexRoll>0) {
                    gameScreen.board.hexTiles.get(i).rollText = new Text(Integer.toString(gameScreen.board.hexTiles.get(i).hexRoll));
                    gameScreen.board.hexTiles.get(i).rollPane.getChildren().add(gameScreen.board.hexTiles.get(i).rollText);
                }

            }
        }
        ImagePattern robber = new ImagePattern(new Image("images/pawn.png"));
        hex.rollPane.getChildren().remove(hex.rollText);
        hex.rollCircle.setFill(robber);
        hex.bandit = true;
        gameStage = MAIN_GAME;
        ArrayList<Player> robberPlayers = new ArrayList<>();
        for(int i = 0; i<hex.connectedCities.size();i++){
            if(hex.connectedCities.get(i).isOwned()){
                if(!robberPlayers.contains(hex.connectedCities.get(i).getOwner())&&!hex.connectedCities.get(i).getOwner().equals(currentPlayer)){
                    robberPlayers.add(hex.connectedCities.get(i).getOwner());
                }
            }

        }
        if(robberPlayers.size()>0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Move Robber");
            alert.setHeaderText("Choose a player to steal from");
            alert.setContentText("You will steal one resource from the chosen player");

            ArrayList<ButtonType> buttonArray = new ArrayList<>();
            for (int i = 0; i < robberPlayers.size(); i++){
                ButtonType buttonType = new ButtonType(robberPlayers.get(i).getPlayerName());
                alert.getButtonTypes().set(i,buttonType);
            }

            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                String stolenResource;
                ArrayList<String> availableResources = new ArrayList<>();
                for(int i = 0; i<robberPlayers.size();i++){
                    if(robberPlayers.get(i).getPlayerName().equals(result.get().getText())){
                        Player robbedPlayer = robberPlayers.get(i);
                        for (int k=0; k<robbedPlayer.getPlayerSheep(); k++){
                            availableResources.add("sheep");
                        }
                        for (int k=0; k<robbedPlayer.getPlayerBrick(); k++){
                            availableResources.add("brick");
                        }
                        for (int k=0; k<robbedPlayer.getPlayerOre(); k++){
                            availableResources.add("ore");
                        }
                        for (int k=0; k<robbedPlayer.getPlayerWheat(); k++){
                            availableResources.add("wheat");
                        }
                        for (int k=0; k<robbedPlayer.getPlayerWood(); k++){
                            availableResources.add("wood");
                        }
                        Random rand = new Random();
                        stolenResource = availableResources.get(rand.nextInt(availableResources.size()));
                        switch(stolenResource){
                            case "sheep":
                                currentPlayer.setPlayerSheep(currentPlayer.getPlayerSheep()+1);
                                robbedPlayer.setPlayerSheep(robbedPlayer.getPlayerSheep()-1);
                                break;
                            case "brick":
                                currentPlayer.setPlayerBrick(currentPlayer.getPlayerBrick()+1);
                                robbedPlayer.setPlayerBrick(robbedPlayer.getPlayerBrick()-1);
                                break;
                            case "ore":
                                currentPlayer.setPlayerOre(currentPlayer.getPlayerOre()+1);
                                robbedPlayer.setPlayerOre(robbedPlayer.getPlayerOre()-1);
                                break;
                            case "wheat":
                                currentPlayer.setPlayerWheat(currentPlayer.getPlayerWheat()+1);
                                robbedPlayer.setPlayerWheat(robbedPlayer.getPlayerWheat()-1);
                                break;
                            case "wood":
                                currentPlayer.setPlayerWood(currentPlayer.getPlayerWood()+1);
                                robbedPlayer.setPlayerWood(robbedPlayer.getPlayerWood()-1);
                                break;
                            default:
                                stolenResource="nothing";
                                break;


                        }
                        Alert conclusion = new Alert(Alert.AlertType.INFORMATION);
                        conclusion.setTitle("Move Robber");
                        conclusion.setHeaderText("Congratulations!");
                        conclusion.setContentText("You have stolen "+stolenResource+"!");
                        conclusion.showAndWait();


                    }
                }
            }

        }


    }

    private void updatePlayer(){
        switch(numCurrentPlayer){
            case 1:
                numCurrentPlayer = 2;
                currentPlayer = player2;
                break;
            case 2:
                numCurrentPlayer = 3;
                currentPlayer = player3;
                break;
            case 3:
                numCurrentPlayer = 4;
                currentPlayer = player4;
                break;
            case 4:
                numCurrentPlayer = 1;
                currentPlayer = player1;
                break;
            default:
                break;
        }
    }

    private void cityPurchase(Player player, City city){
        if (!city.owned && city.available) {
            if (player.getPlayerBrick() > 1 && player.getPlayerWheat() > 1 && player.getPlayerSheep() > 1 && player.getPlayerWood() > 1) {
                if(confirmPurchase("settlement").equals(CONFIRMED)) {
                    player.setPlayerBrick(player.getPlayerBrick() - 1);
                    player.setPlayerWheat(player.getPlayerWheat() - 1);
                    player.setPlayerSheep(player.getPlayerSheep() - 1);
                    player.setPlayerWood(player.getPlayerWood() - 1);
                    city.setOwner(player);
                    ArrayList<Integer> cityInfo = new ArrayList<>();
                    cityInfo.add(city.getCityId());
                    cityInfo.add(city.getCitySize());
                    player.addPlayerCity(cityInfo);
                    gameScreen.resourcePane.update(currentPlayer);
                    updateCityAvailability();
                    updateRoadAvailability();
                    updatePlayerVP();
                }
            }
        }
        if (city.getOwner().equals(currentPlayer)&&city.getCitySize()==1){

            if(confirmPurchase("city").equals(CONFIRMED)){
                if(player.getPlayerOre()>=3&&player.getPlayerWheat()>=2){
                    player.setPlayerOre(player.getPlayerOre()-3);
                    player.setPlayerWheat(player.getPlayerWheat()-2);
                    city.upgradeCity();

                }
            }
        }

    }

    private void roadPurchase(Player player, Road road){
        if (!road.isOwned() && road.available) {

            if ((gameStage.equals(ROAD_BUILDING))||(player.getPlayerBrick() >1 && player.getPlayerWood()>1)){
                if(confirmPurchase("road").equals(CONFIRMED)) {
                    if (gameStage.equals(MAIN_GAME)) {
                        player.setPlayerBrick(player.getPlayerBrick() - 1);
                        player.setPlayerWood(player.getPlayerWood() - 1);
                    }
                    road.setOwner(player);
                    player.addPlayerRoad(road);
                    gameScreen.resourcePane.update(currentPlayer);
                    updateCityAvailability();
                    updateRoadAvailability();
                }
            }
        }



    }

    private void updatePlayerVP(){
        player1.updateVP();
        player2.updateVP();
        player3.updateVP();
        player4.updateVP();
        if(player1.getPlayerVP()>=10||player2.getPlayerVP()>=10||player3.getPlayerVP()>=10||player4.getPlayerVP()>=10){
            endGame();
        }
        gameScreen.playerPane.updateVP(player1.getPlayerVP(),player2.getPlayerVP(), player3.getPlayerVP(), player4.getPlayerVP());
    }

    private void endGame(){
        Player winningPlayer = player1;
        for (int i = 0; i< playerList.size(); i++){
            if (playerList.get(i).getPlayerVP()>=10){
                winningPlayer=playerList.get(i);

            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Victory!");
        alert.setHeaderText("Congratulations " + winningPlayer.getPlayerName());
        alert.setContentText("You are the winner!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()){
            stage.close();

        }


    }

    private void purchaseCard(Player player){
        if(player.getPlayerSheep()>0&&player.getPlayerOre()>0&&player.getPlayerWheat()>0){
            //dialog purchase check
            if(confirmPurchase("development card").equals(CONFIRMED)) {
                player.setPlayerSheep(player.getPlayerSheep() - 1);
                player.setPlayerOre(player.getPlayerOre() - 1);
                player.setPlayerWheat(player.getPlayerWheat() - 1);
                Random rand = new Random();
                int card = rand.nextInt(deck.size());
                String cardName = deck.get(card);
                deck.remove(card);
                switch (cardName) {
                    case ("knight"):
                        player.setPlayerKnightCard(player.getPlayerKnightCard() + 1);
                        break;
                    case ("vpCard"):
                        player.setPlayerVPCard(player.getPlayerVPCard() + 1);
                        break;
                    case ("roadBuilding"):
                        player.setPlayerRoadBuildingCard(player.getPlayerRoadBuildingCard() + 1);
                        break;
                    case ("monopoly"):
                        player.setPlayerMonopolyCard(player.getPlayerMonopolyCard() + 1);
                        break;
                    case ("yearOfPlenty"):
                        player.setPlayerYearOfPlentyCard(player.getPlayerYearOfPlentyCard() + 1);
                        break;
                }
                gameScreen.resourcePane.update(currentPlayer);
            }
        }

    }

    private void tradeResource(String resource){
        if(currentPlayer.getPlayerBrick()+currentPlayer.getPlayerOre()+currentPlayer.getPlayerSheep()+currentPlayer.getPlayerWheat()+currentPlayer.getPlayerWood()>=4){
            ResourceTradeDialog dialog = new ResourceTradeDialog(resource, currentPlayer);
            Optional<Integer> result =  dialog.showAndWait();
            gameScreen.resourcePane.update(currentPlayer);

        }

    }

    private void playCard(String card){
        switch(card){
            case "knight":
                if(currentPlayer.getPlayerKnightCard()>0){
                    currentPlayer.setPlayerKnightCard(currentPlayer.getPlayerKnightCard()-1);
                    knightCard();

                }
                break;
            case "VPCard":
                if(currentPlayer.getPlayerVPCard()>0){
                    currentPlayer.setPlayerVPCard(currentPlayer.getPlayerVPCard()-1);
                    currentPlayer.incrementPlayedVPCard();
                }
                break;
            case "roadBuilding":
                if(currentPlayer.getPlayerRoadBuildingCard()>0){
                    currentPlayer.setPlayerRoadBuildingCard(currentPlayer.getPlayerRoadBuildingCard()-1);
                    roadBuilding();
                }
                break;
            case "monopoly":
                if(currentPlayer.getPlayerMonopolyCard()>0){
                    currentPlayer.setPlayerMonopolyCard(currentPlayer.getPlayerMonopolyCard()-1);
                    monopoly();
                }
                break;
            case "yearOfPlenty":
                if(currentPlayer.getPlayerYearOfPlentyCard()>0){
                    currentPlayer.setPlayerYearOfPlentyCard(currentPlayer.getPlayerYearOfPlentyCard()-1);
                    yearOfPlenty();
                }
                break;
            default:
                break;

        }

    }
    private void knightCard(){
        if(confirmPlayCard("knight card").equals(CONFIRMED)) {
            //selection dialog
            gameStage = BANDIT_UPDATE;
            currentPlayer.incrementPlayerKnights();
            checkLargestArmy();
        }

    }
    private void checkLargestArmy(){

        for(int i =0; i<playerList.size(); i++){
            if(playerList.get(i).getPlayerKnights()>previousLargestArmy){
                previousLargestArmy = playerList.get(i).getPlayerKnights();
                playerLargestArmy = i;
                playerList.get(i).setLargestArmy(true);
            } else {
                playerList.get(i).setLargestArmy(false);
            }
        }
        updatePlayerVP();

    }
    private void roadBuilding(){
        if(confirmPlayCard("roadBuilding").equals(CONFIRMED)) {
            //road building dialog
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Road Building Card");
            alert.setContentText("You may build two roads for free. Please do so before continuing");
            alert.showAndWait();
            gameStage = ROAD_BUILDING;
            remainingRoadBuildingRoads = 2;
            disableCities();
        }


    }
    private void monopoly(){
        if (confirmPlayCard("monopoly").equals(CONFIRMED)) {
            //dialog select resource
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Monopoly Development Card");
            alert.setHeaderText("Choose a resource type. You will take all of that resource from all players.");

            ButtonType wood = new ButtonType("wood");
            ButtonType brick = new ButtonType("brick");
            ButtonType ore = new ButtonType("ore");
            ButtonType sheep = new ButtonType("sheep");
            ButtonType wheat = new ButtonType("wheat");
            alert.getButtonTypes().setAll(wood, brick, ore, sheep, wheat);

            Optional<ButtonType> result = alert.showAndWait();

            int resourceTotal = 0;
            if (result.isPresent()) {
                String resource = result.get().getText().toString();
                for (int i = 0; i < playerList.size(); i++) {
                    switch (resource) {
                        case ("wood"):
                            resourceTotal += playerList.get(i).getPlayerWood();
                            playerList.get(i).setPlayerWood(0);
                            break;
                        case ("brick"):
                            resourceTotal += playerList.get(i).getPlayerBrick();
                            playerList.get(i).setPlayerBrick(0);
                            break;
                        case ("ore"):
                            resourceTotal += playerList.get(i).getPlayerOre();
                            playerList.get(i).setPlayerOre(0);
                            break;
                        case ("sheep"):
                            resourceTotal += playerList.get(i).getPlayerSheep();
                            playerList.get(i).setPlayerSheep(0);
                            break;
                        case ("wheat"):
                            resourceTotal += playerList.get(i).getPlayerWheat();
                            playerList.get(i).setPlayerWheat(0);
                            break;
                        default:
                            break;
                    }
                }
                switch (resource) {
                    case ("wood"):
                        currentPlayer.setPlayerWood(resourceTotal);
                        break;
                    case ("brick"):
                        currentPlayer.setPlayerBrick(resourceTotal);
                        break;
                    case ("ore"):
                        currentPlayer.setPlayerOre(resourceTotal);
                        break;
                    case ("sheep"):
                        currentPlayer.setPlayerSheep(resourceTotal);
                        break;
                    case ("wheat"):
                        currentPlayer.setPlayerWheat(resourceTotal);
                        break;
                    default:
                        break;
                }
            }
        }

    }
    private void yearOfPlenty(){
        if (confirmPlayCard("yearOfPlenty").equals(CONFIRMED)) {
            for (int i = 0; i < 2; i++) {
                //dialog select resource
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Year of Plenty Development Card");
                alert.setHeaderText("Choose a resource type. That resource will be added to your supply");

                ButtonType wood = new ButtonType("wood");
                ButtonType brick = new ButtonType("brick");
                ButtonType ore = new ButtonType("ore");
                ButtonType sheep = new ButtonType("sheep");
                ButtonType wheat = new ButtonType("wheat");
                alert.getButtonTypes().setAll(wood, brick, ore, sheep, wheat);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    String resource = result.get().getText().toString();
                    switch (resource) {
                        case ("wood"):
                            currentPlayer.setPlayerWood(currentPlayer.getPlayerWood() + 1);
                            break;
                        case ("brick"):
                            currentPlayer.setPlayerBrick(currentPlayer.getPlayerBrick() + 1);
                            break;
                        case ("ore"):
                            currentPlayer.setPlayerOre(currentPlayer.getPlayerOre() + 1);
                            break;
                        case ("sheep"):
                            currentPlayer.setPlayerSheep(currentPlayer.getPlayerSheep() + 1);
                            break;
                        case ("wheat"):
                            currentPlayer.setPlayerWheat(currentPlayer.getPlayerWheat() + 1);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

    }

    private String confirmPlayCard(String card){
        String confirmation="NO";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Play Chosen Card?");
        alert.setHeaderText("You have chosen to play a " + card + " card.");
        alert.setContentText("Is this correct?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                confirmation = CONFIRMED;
            }
        }
        return confirmation;
    }

    private String confirmPurchase(String purchaseType){
        String confirmation = "NO";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm purchase");
        alert.setHeaderText("You have chose to purchase a " + purchaseType + ".");
        alert.setContentText("Is this correct?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                confirmation = CONFIRMED;
            }
        }
        return confirmation;
    }
}
