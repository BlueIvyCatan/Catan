package BlueIvyCatan.gameObjects;

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
    String setUpStage;
    final String SETTLEMENT = "SETTLEMENT";
    final String ROAD = "ROAD";
    Player currentPlayer;
    int numPlayers;
    int numCurrentPlayer;
    int setUpRound;
    City setUpCity;
    ArrayList<Player> playerList = new ArrayList<>();

    public Game(Stage primaryStage) {
        stage = primaryStage;
        initGameVariables();
//        stage.getScene().getRoot().setStyle("-fx-background-color: red");
        stage.getScene().setRoot(title);
        setMouseListeners();




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
        player2.setPlayerColor(Color.GREEN);
        player3.setPlayerColor(Color.YELLOW);
        player4.setPlayerColor(Color.RED);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);



        title = new TitleScreen(game.getHeight(), game.getWidth());
        playerScreen = new PlayerScreen(game.getHeight(), game.getWidth());
        gameScreen = new GameScreen(game.getHeight(), game.getWidth());
        gameScreen.resourcePane.dice = dice;
        gameScreen.resourcePane.setDiceParameters();
        updateCityAvailability();



    }

    public double getWidth(){return width;}

    public double getHeight(){return height;}

    public void nextTurn(){
        updatePlayer();
        if (currentPlayer.getPlayerPassword()!="none") {
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
                return new String(password.getText());
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
        } while (Password.toString()!=currentPlayer.getPlayerPassword());
    }

    public void setMouseListeners(){
        title.btnNewGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                stage.getScene().setRoot(playerScreen);
            }
        });
        playerScreen.btnStartGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                player1.setPlayerName(playerScreen.txtPlayer1Name.getText());
                player2.setPlayerName(playerScreen.txtPlayer2Name.getText());
                player3.setPlayerName(playerScreen.txtPlayer3Name.getText());
                player4.setPlayerName(playerScreen.txtPlayer4Name.getText());

                player1.setPlayerPassword(playerScreen.txtPlayer1Password.getText());
                player2.setPlayerPassword(playerScreen.txtPlayer2Password.getText());
                player3.setPlayerPassword(playerScreen.txtPlayer3Password.getText());
                player4.setPlayerPassword(playerScreen.txtPlayer4Password.getText());
                stage.getScene().setRoot(gameScreen);
            }
        });
        gameScreen.endGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.close();
            }
        });

        gameScreen.playerPane.btnNextTurn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage == MAIN_GAME) {
                    updatePlayerVP();
                    nextTurn();
                }
            }
        });
        for(int i = 0; i < gameScreen.board.cityList.size(); i++ ){
            gameScreen.board.cityList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (gameStage == SETUP) {
                            setUpSettlementPurchase((City) event.getSource());


                        } else if (gameStage == MAIN_GAME) {
                            cityPurchase(currentPlayer, (City) event.getSource());

                        }
                    }

            });
        }

        for (int i = 0; i < gameScreen.board.roadList.size(); i++){
            gameScreen.board.roadList.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (gameStage == SETUP) {
                        setUpRoadPurchase((Road) event.getSource());
                    } else if (gameStage == MAIN_GAME) {
                        roadPurchase(currentPlayer, (Road) event.getSource());
                    }
                }
            });
        }

        for (int i = 0; i < gameScreen.board.hexTiles.size(); i++){
            gameScreen.board.hexTiles.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (gameStage == BANDIT_UPDATE){
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
                if (gameStage == MAIN_GAME) {
                    tradeResource("brick");
                }
            }
        });
        gameScreen.resourcePane.sheep.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage == MAIN_GAME){
                    tradeResource("sheep");
                }
            }
        });
        gameScreen.resourcePane.ore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage == MAIN_GAME) {
                    tradeResource("ore");
                }
            }
        });
        gameScreen.resourcePane.wood.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage == MAIN_GAME){
                    tradeResource("wood");
                }
            }
        });
        gameScreen.resourcePane.wheat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameStage == MAIN_GAME) {
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
                playCard("VPCard");
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
            if(setUpStage == SETTLEMENT) {
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
            if(setUpStage == ROAD) {
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
            if ((gameScreen.board.cityList.get(i).isOwned() && gameStage==SETUP) || (gameScreen.board.cityList.get(i).getOwner()==currentPlayer && gameStage==MAIN_GAME)){
                ownedCities.add(gameScreen.board.cityList.get(i));
            } else {
                if (gameStage == SETUP) {
                    gameScreen.board.cityList.get(i).available = true;
                    gameScreen.board.cityList.get(i).clip.setFill(Color.AQUA);
                } else if (gameStage == MAIN_GAME){
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
        if (gameStage == SETUP){
            for (int i = 0; i < setUpCity.connectedRoads.size(); i++){
                for (int k = 0; k < gameScreen.board.roadList.size(); k++){
                    if(setUpCity.connectedRoads.get(i) == gameScreen.board.roadList.get(k)){
                        gameScreen.board.roadList.get(k).setFill(Color.AQUA);
                        gameScreen.board.roadList.get(k).available = true;
                    }
                }
            }

        } else if (gameStage == MAIN_GAME){
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
            for(int k = 0; k <gameScreen.board.hexTiles.get(i).connectedCities.size(); k++){
                for(int l = 0; l <gameScreen.board.cityList.size(); l++){
                    if(gameScreen.board.hexTiles.get(i).connectedCities.get(k) == gameScreen.board.cityList.get(l).getCityId()){
                        if(gameScreen.board.cityList.get(l).isOwned()) {
                            gameScreen.board.cityList.get(l).owner.addPlayerResource(gameScreen.board.hexTiles.get(i).hexType, 1);
                        }
                    }
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
        }
        for(int i=0; i <gameScreen.board.hexTiles.size(); i++){
            if(gameScreen.board.hexTiles.get(i).getHexRoll()==dice.getDiceResult()){
                for (int k = 0; k<gameScreen.board.hexTiles.get(i).connectedCities.size();k++){
                    for(int l = 0; l < gameScreen.board.cityList.size(); l++){
                        if (gameScreen.board.hexTiles.get(i).connectedCities.get(k) == gameScreen.board.cityList.get(l).getCityId()){
                            if(gameScreen.board.cityList.get(l).isOwned()){
                                gameScreen.board.cityList.get(l).owner.addPlayerResource(gameScreen.board.hexTiles.get(i).hexType,gameScreen.board.cityList.get(l).getCitySize());
                            }
                        }
                    }
                }
            }
        }
        updateScreen(player);
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
        gameStage = BANDIT_UPDATE;

    }

    private void moveBandit(HexTile hex){
        for (int i = 0; i < gameScreen.board.hexTiles.size(); i++){
            if (gameScreen.board.hexTiles.get(i).bandit){
                gameScreen.board.hexTiles.get(i).bandit = false;
                gameScreen.board.hexTiles.get(i).rollText = new Text(Integer.toString(gameScreen.board.hexTiles.get(i).hexRoll));
                gameScreen.board.hexTiles.get(i).rollPane.getChildren().add(gameScreen.board.hexTiles.get(i).rollText);
            }
        }
        ImagePattern robber = new ImagePattern(new Image("BlueIvyCatan/images/pawn.png"));
        hex.rollPane.getChildren().remove(hex.rollText);
        hex.rollCircle.setFill(robber);
    }

    private void updatePlayer(){
        switch(numCurrentPlayer){
            case 1:
                numCurrentPlayer = 2;
                currentPlayer = player2;
            case 2:
                numCurrentPlayer = 3;
                currentPlayer = player3;
            case 3:
                numCurrentPlayer = 4;
                currentPlayer = player4;
            case 4:
                numCurrentPlayer = 1;
                currentPlayer = player1;
            default:
                break;
        }
    }

    private void cityPurchase(Player player, City city){
        if (!city.owned && city.available) {
            if (player.getPlayerBrick() > 1 && player.getPlayerWheat() > 1 && player.getPlayerSheep() > 1 && player.getPlayerWood() > 1) {
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

    private void roadPurchase(Player player, Road road){
        if (!road.isOwned() && road.available) {
            if (player.getPlayerBrick() >1 && player.getPlayerWood()>1){
                player.setPlayerBrick(player.getPlayerBrick() - 1);
                player.setPlayerWood(player.getPlayerWood() - 1);
                road.setOwner(player);
                player.addPlayerRoad(road);
                gameScreen.resourcePane.update(currentPlayer);
                updateCityAvailability();
                updateRoadAvailability();
            }
        }



    }

    private void updatePlayerVP(){
        player1.updateVP();
        player2.updateVP();
        player3.updateVP();
        player4.updateVP();
        gameScreen.playerPane.updateVP(player1.getPlayerVP(),player2.getPlayerVP(), player3.getPlayerVP(), player4.getPlayerVP());
    }

    private void purchaseCard(Player player){

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
                }
                break;
            case "monopoly":
                if(currentPlayer.getPlayerMonopolyCard()>0){
                    currentPlayer.setPlayerMonopolyCard(currentPlayer.getPlayerMonopolyCard()-1);
                }
                break;
            case "yearOfPlenty":
                if(currentPlayer.getPlayerYearOfPlentyCard()>0){
                    currentPlayer.setPlayerYearOfPlentyCard(currentPlayer.getPlayerYearOfPlentyCard()-1);
                }
                break;
            default:
                break;

        }

    }
}
