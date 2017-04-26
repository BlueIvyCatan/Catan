package gameObjects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        initScreen(primaryStage);

        Game game = new Game(primaryStage);

//        do {
//            game.newGame();
//
//        } while (!game.endGame);



//        GameScreen gameScreen = new GameScreen();
//
//        gameScreen.initializeGameScreen();
//        Scene scene = new Scene(gameScreen);
//
//
//        primaryStage.setScene(scene);
//
//        primaryStage.setTitle("Blue Ivy Catan");
//        primaryStage.setResizable(false);
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void initScreen(Stage primaryStage){
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: tan;");
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Blue Ivy Catan");
        primaryStage.setMaximized(true);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

