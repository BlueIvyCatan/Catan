package BlueIvyCatan.gameObjects;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

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
    String setUpStage;
    final String SETTLEMENT = "SETTLEMENT";
    final String ROAD = "ROAD";
    Player currentPlayer;
    int numPlayers;
    int numCurrentPlayer;
    int setUpRound;
    City setUpCity;

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
        player4.setPlayerColor(Color.MAGENTA);



        title = new TitleScreen(game.getHeight(), game.getWidth());
        playerScreen = new PlayerScreen(game.getHeight(), game.getWidth());
        gameScreen = new GameScreen(game.getHeight(), game.getWidth());
        gameScreen.resourcePane.dice = dice;
        gameScreen.resourcePane.setDiceParameters();



    }

    public double getWidth(){return width;}

    public double getHeight(){return height;}

    public void nextTurn(){
        updatePlayer();
        beginTurn(currentPlayer);

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
                            checkPurchase(currentPlayer);


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
                    }
                }
            });
        }

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
                currentPlayer.addPlayerRoad(road.getRoadId());
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
            if (gameScreen.board.cityList.get(i).isOwned()){
                ownedCities.add(gameScreen.board.cityList.get(i));
            } else {
                gameScreen.board.cityList.get(i).available= true;
                gameScreen.board.cityList.get(i).clip.setFill(Color.AQUA);
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
                    if(setUpCity.connectedRoads.get(i) == gameScreen.board.roadList.get(k).getRoadId()){
                        gameScreen.board.roadList.get(k).setFill(Color.AQUA);
                    }
                }
            }

        }



    }

    private void disableCities(){
        for(int i = 0; i<gameScreen.board.cityList.size(); i++){
            if(!gameScreen.board.cityList.get(i).isOwned()){
                gameScreen.board.cityList.get(i).clip.setFill(Color.BLACK);

            }
        }
    }

    private void disableRoads(){
        for(int i = 0; i<gameScreen.board.roadList.size(); i++){
            if(!gameScreen.board.roadList.get(i).isOwned()){
                gameScreen.board.roadList.get(i).setFill(Color.BLACK);
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
        gameScreen.resourcePane.updateDice();
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

    private void checkPurchase(Player player){

    }

    private void updatePlayerVP(){
        player1.updateVP();
        player2.updateVP();
        player3.updateVP();
        player4.updateVP();
        gameScreen.playerPane.updateVP(player1.getPlayerVP(),player2.getPlayerVP(), player3.getPlayerVP(), player4.getPlayerVP());
    }
}
