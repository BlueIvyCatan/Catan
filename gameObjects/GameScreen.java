package gameObjects;

import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;

/**
 * Created by Tyler on 2/21/2017.
 */
public class GameScreen extends GridPane{

    double screenWidth;
    double screenHeight;
    Button endGame;
    ResourcePane resourcePane;
    BoardPane board;
    PlayerPane playerPane;
    ArrayList<Player> players;

    public GameScreen (double height, double width, ArrayList<Player> playerList){
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        screenWidth = screenSize.getWidth()*.9;
//        screenHeight = screenSize.getHeight()*.9;
        players = playerList;
        screenWidth = width;
        screenHeight = height;
        endGame = new Button("End Game");
        endGame.setText("End Game");
        endGame.setLayoutX(width - endGame.getWidth());
        endGame.setLayoutY(height - endGame.getHeight());
        resourcePane = new ResourcePane(screenHeight*.7, screenWidth *.2, players);
        board = new BoardPane(screenWidth*.8, screenHeight);
        playerPane = new PlayerPane(screenHeight*.3, screenWidth*.2, players);


//        this.setPrefSize(screenWidth,screenHeight);
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();
        column1.setPercentWidth(20);
        column2.setPercentWidth(80);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setPercentHeight(30);
        row2.setPercentHeight(70);
        this.getColumnConstraints().addAll(column1, column2);
        this.getRowConstraints().addAll(row1, row2);
//        this.setRowIndex(playerPane, 1);
//        this.setColumnIndex(playerPane, 1);
//        this.setRowIndex(resourcePane, 2);
//        this.setColumnIndex(resourcePane, 1);
//        this.setRowIndex(board, 1);
//        this.setRowSpan(board, 2);
//        this.setColumnIndex(board, 2);
//        this.getChildren().addAll(resourcePane, board, playerPane);
        this.add(playerPane,0,0);
        this.add(resourcePane,0,1);
        this.add(board,1,0,1,2);
        this.add(endGame,1,0);
//        this.setGridLinesVisible(true);



    }

    public void update(Player player){
        resourcePane.update(player);
//        board.update(player);
//        playerPane.update(player);
    }




}
