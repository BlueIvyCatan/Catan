package gameObjects;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * Created by Tyler on 3/6/2017.
 */
public class PlayerPane extends GridPane {
    double paneHeight;
    double paneWidth;
    Button btnNextTurn = new Button();
    double rowHeight;
    double columnWidth;
    GridPane playerPane;
    ArrayList<Text> playerNameText = new ArrayList<>();
    ArrayList<Player> players;


    public PlayerPane(double height, double width, ArrayList<Player> playerList){
        playerPane = this;
        players = playerList;

        paneHeight = height;
        paneWidth = width;
        rowHeight = paneHeight/4;
        columnWidth = paneWidth/4;
        btnNextTurn.setPrefHeight(rowHeight);
        btnNextTurn.setPrefWidth(paneWidth);
        btnNextTurn.setText("Next Turn");
        ArrayList<Color> playerColors = new ArrayList<>();
        playerColors.add(Color.BLUE);
        playerColors.add(Color.RED);
        playerColors.add(Color.YELLOW);
        playerColors.add(Color.GREEN);
        this.add(btnNextTurn,0,3,4,1);

//        this.setStyle("-fx-background-color: green;");
        ColumnConstraints player1 = new ColumnConstraints();
        ColumnConstraints player2 = new ColumnConstraints();
        ColumnConstraints player3 = new ColumnConstraints();
        ColumnConstraints player4 = new ColumnConstraints();
        RowConstraints playerIcon = new RowConstraints();
        RowConstraints playerName = new RowConstraints();
        RowConstraints playerVP = new RowConstraints();
        RowConstraints playerHand = new RowConstraints();
        player1.setPercentWidth(25);
        player2.setPercentWidth(25);
        player3.setPercentWidth(25);
        player4.setPercentWidth(25);
        playerIcon.setPercentHeight(25);
        playerName.setPercentHeight(25);
        playerVP.setPercentHeight(25);
        playerHand.setPercentHeight(25);
        this.getRowConstraints().addAll(playerIcon, playerName, playerVP);
        this.getColumnConstraints().addAll(player1, player2, player3, player4);
        ArrayList<StackPane> playerIconContainers = new ArrayList<StackPane>();
        ArrayList<StackPane> playerNamesContainers = new ArrayList<StackPane>();
        ArrayList<StackPane> playerVPContainers = new ArrayList<StackPane>();
        playerIconContainers.add(0,new StackPane());
        playerIconContainers.add(1,new StackPane());
        playerIconContainers.add(2,new StackPane());
        playerIconContainers.add(3,new StackPane());
//        this.setGridLinesVisible(true);

//        Image image = new Image("images/pawn.png",columnWidth,rowHeight,false,false);


        for(int i = 0; i < playerIconContainers.size();i++){
            StackPane pane = playerIconContainers.get(i);
            Pane icon = new Pane();
            Image image = players.get(i).playerIcon;
            BackgroundSize size = new BackgroundSize(columnWidth*.9, rowHeight*.9,false,false, false, false);
            BackgroundImage iconBI = new BackgroundImage(players.get(i).playerIcon,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,size);
            icon.setBackground(new Background(iconBI));
            Rectangle r = new Rectangle(columnWidth,rowHeight);
            r.setFill(Color.WHITE);
            r.setStroke(Color.BLACK);

            pane.getChildren().addAll(r,icon);


            this.add(pane, i,0);

        }
        for (int i = 0; i <4 ;i++){
            StackPane s = new StackPane();
            Rectangle r = new Rectangle();
            BackgroundFill bf = new BackgroundFill(playerColors.get(i), CornerRadii.EMPTY, Insets.EMPTY);
            Background b = new Background(bf);
            r.setFill(playerColors.get(i));
            Text t = new Text();
            t.setText(players.get(i).getPlayerName());
            t.setTextAlignment(TextAlignment.CENTER);
            t.setFill(Color.BLACK);
            t.setStyle("-fx-font-weight: bold");
            playerNameText.add(t);

            s.setBackground(b);
            s.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            s.getChildren().addAll(t,r);
            this.add(s,i,1);
            GridPane.setHalignment(t, HPos.CENTER);
            GridPane.setValignment(t, VPos.CENTER);

        }

    }
    public void update(Player player){
        playerPane.setBackground(new Background(new BackgroundFill(player.playerColor,CornerRadii.EMPTY,Insets.EMPTY)));


    }

    public void updateVP(int player1VP, int player2VP, int player3VP, int player4VP){
        Text txtPlayer1VP = new Text(Integer.toString(player1VP));
        GridPane.setHalignment(txtPlayer1VP, HPos.CENTER);
        GridPane.setValignment(txtPlayer1VP, VPos.CENTER);
        playerPane.add(txtPlayer1VP,0,2);
        Text txtPlayer2VP = new Text(Integer.toString(player2VP));
        GridPane.setHalignment(txtPlayer2VP, HPos.CENTER);
        GridPane.setValignment(txtPlayer2VP, VPos.CENTER);
        playerPane.add(txtPlayer2VP,1,2);
        Text txtPlayer3VP = new Text(Integer.toString(player3VP));
        GridPane.setHalignment(txtPlayer3VP, HPos.CENTER);
        GridPane.setValignment(txtPlayer3VP, VPos.CENTER);
        playerPane.add(txtPlayer3VP, 2, 2);
        Text txtPlayer4VP = new Text(Integer.toString(player4VP));
        GridPane.setHalignment(txtPlayer4VP, HPos.CENTER);
        GridPane.setValignment(txtPlayer4VP, VPos.CENTER);
        playerPane.add(txtPlayer4VP,3,2);


    }
}
