package BlueIvyCatan;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * Created by Tyler on 3/6/2017.
 */
public class PlayerPane extends GridPane {

    public PlayerPane(){
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
        this.setGridLinesVisible(true);

        Image image = new Image("BlueIvyCatan/pawn.png",80,60,false,false);


        for(int i = 0; i < playerIconContainers.size();i++){
            StackPane pane = playerIconContainers.get(i);
            Pane icon = new Pane();
            BackgroundImage iconBI = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
            icon.setBackground(new Background(iconBI));
            Rectangle r = new Rectangle(50,50);
            r.setFill(Color.WHITE);
            pane.getChildren().addAll(r,icon);


            this.add(pane, i,0);

        }
        for (int i = 0; i <4 ;i++){
            StackPane s = new StackPane();
            Rectangle r = new Rectangle();
            BackgroundFill bf = new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY);
            Background b = new Background(bf);
            r.setFill(Color.AQUA);
            Text t = new Text();
            t.setText("Player"+Integer.toString(i));
            t.setTextAlignment(TextAlignment.CENTER);
            t.setFill(Color.RED);

            s.setBackground(b);
            s.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            s.getChildren().addAll(t,r);
            this.add(s,i,1);
            GridPane.setHalignment(t, HPos.CENTER);
            GridPane.setValignment(t, VPos.CENTER);

        }
    }
}
