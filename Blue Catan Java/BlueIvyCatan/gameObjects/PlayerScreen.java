package BlueIvyCatan.gameObjects;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Tyler on 2/21/2017.
 */
public class PlayerScreen extends Pane {
    Button btnStartGame;
    TextField txtPlayer1Name;
    TextField txtPlayer2Name;
    TextField txtPlayer3Name;
    TextField txtPlayer4Name;

    TextField txtPlayer1Password;
    TextField txtPlayer2Password;
    TextField txtPlayer3Password;
    TextField txtPlayer4Password;
    public PlayerScreen(double height, double width){
        try {
            TitledPane gridPane = (TitledPane) FXMLLoader.load(PlayerScreen.class.getResource("/BlueIvyCatan/view/Catan Login.fxml"));
            gridPane.setPrefWidth(width);
            gridPane.setPrefHeight(height);
            btnStartGame = (Button) gridPane.lookup("#btnLogin");
            txtPlayer1Name = (TextField) gridPane.lookup("#txtPlayer1");
            txtPlayer2Name = (TextField) gridPane.lookup("#txtPlayer2");
            txtPlayer3Name = (TextField) gridPane.lookup("#txtPlayer3");
            txtPlayer4Name = (TextField) gridPane.lookup("#txtPlayer4");
            txtPlayer1Password = (TextField) gridPane.lookup("#pswField1");
            txtPlayer2Password = (TextField) gridPane.lookup("#pswField2");
            txtPlayer3Password = (TextField) gridPane.lookup("#pswField3");
            txtPlayer4Password = (TextField) gridPane.lookup("#pswField4");
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
