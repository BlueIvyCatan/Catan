package gameObjects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;

import java.awt.*;

public class Controller {
    @FXML
    private Button btnStartGame;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        if (event.getSource() == btnStartGame){

        }
    }
}
