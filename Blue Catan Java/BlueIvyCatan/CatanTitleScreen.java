/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catan.title.screen;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author amznj
 */
public class CatanTitleScreen extends Application {
    Button btnStartGame;
    Stage window;
    Label lblWelcomeMsg = new Label();
    @Override
//    public CatanTitleScreen extends Pane(double height, double width){
        
//  }
    public void start(Stage primaryStage) {
        window = primaryStage;
//        lblWelcomeMsg.setText("Welcome to the Settlers of Catan! \nWhen you are"
//                + " ready to start a new game, \nclick the button below.");
//        lblWelcomeMsg.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");
        btnStartGame = new Button();
        btnStartGame.setStyle("-fx-font-size: 18px; font-weight: bold;");
        btnStartGame.setText("Start Game");
        btnStartGame.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("Hello World!");
            }
        });
        
        Pane root = new Pane(); // remove
        root.setStyle("-fx-background-image: url('https://github.com/BlueIvyCatan/Catan/blob/BlueIvyCatan-04122017/Blue%20Catan%20Java/src/BlueIvyCatan/images/catanTitleScreenImage.png?raw=true'); "
                + "-fx-background-color: red; "
                + "-fx-background-repeat: no-repeat; "
                + "-fx-background-position: center; "
                + "-fx-background-size: 55%; "
                + "-fx-background-attatchment: fixed;");
        root.getChildren().addAll(btnStartGame, lblWelcomeMsg);
                
        Scene scene = new Scene(root, 700, 500);
        
        window.setTitle("Settlers of Catan");
        window.setScene(scene);
        window.show();
        lblWelcomeMsg.setTextAlignment(TextAlignment.CENTER);
        btnStartGame.setLayoutX(300);
        btnStartGame.setLayoutY(300);
        lblWelcomeMsg.setLayoutX(160);
        lblWelcomeMsg.setLayoutY(200);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
