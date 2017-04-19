package BlueIvyCatan.gameObjects;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Tyler on 2/21/2017.
 */
public class PlayerScreen extends Pane {
    Button btnStartGame;
    public PlayerScreen(double height, double width){
        try {
            GridPane gridPane = (GridPane) FXMLLoader.load(PlayerScreen.class.getResource("/BlueIvyCatan/view/PlayerScreen.fxml"));
            gridPane.setPrefWidth(width);
            gridPane.setPrefHeight(height);
            btnStartGame = (Button) gridPane.lookup("#btnStartGame");
            this.getChildren().add(gridPane);
//            btnStartGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//
//                }
//            });
//            gridPane.getChildren().g

        } catch (IOException e) {
            e.printStackTrace();
        }



//        FXMLLoader loader = new FXMLLoader();
//        Node root = loader.getRoot();
//        loader.setRoot(this);
//        loader.setController(this);
//        loader.setLocation(PlayerScreen.class.getResource("PlayerScreen.fxml"));
//        try {
//            loader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
////        this.getChildren().add(root);


    }
}
