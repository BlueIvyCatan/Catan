package BlueIvyCatan;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by Tyler on 3/29/2017.
 */
public class Game {
    Player player1 = new Player();
    Player player2 = new Player();
    Player player3 = new Player();
    Player player4 = new Player();
    boolean endGame;
    Stage stage;
    private double width;
    private double height;
    private Game game;
    private PlayerScreen playerScreen;
    private TitleScreen title;
    private GameScreen gameScreen;

    public Game(Stage primaryStage) {
        stage = primaryStage;
        initGameVariables();
//        stage.getScene().getRoot().setStyle("-fx-background-color: red");
        stage.getScene().setRoot(title);
        title.getChildren().get(0).setOnMouseClicked(new EventHandler<MouseEvent>() {
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
                primaryStage.close();
            }
        });


    }





    public void initGameVariables(){
        game = this;
        Scene scene = stage.getScene();
        width = scene.getWidth();
        height = scene.getHeight();
        title = new TitleScreen(game.getHeight(), game.getWidth());
        playerScreen = new PlayerScreen(game.getHeight(), game.getWidth());
        gameScreen = new GameScreen(game.getHeight(), game.getWidth());


    }

    public double getWidth(){return width;}

    public double getHeight(){return height;}
}
