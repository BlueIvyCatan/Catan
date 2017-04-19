package BlueIvyCatan.gameObjects;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

/**
 * Created by Tyler on 2/21/2017.
 */
public class TitleScreen extends Pane{
    Button btnNewGame;
    public TitleScreen(double height, double width) {
//        this.setStyle("-fx-background-color: red");
        this.setStyle("-fx-background-image: url('BlueIvyCatan/images/catan.jpg'); -fx-background-size: stretch");
        btnNewGame = new Button("Start New Game");
        btnNewGame.setLayoutX(width/2);
        btnNewGame.setLayoutY(height/2);
        this.getChildren().add(btnNewGame);



    }
}
