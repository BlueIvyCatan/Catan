package BlueIvyCatan;

import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Created by Tyler on 3/6/2017.
 */
public class ResourcePane extends GridPane {
    int playerWood;
    int playerOre;
    int playerBrick;
    int playerSheep;
    int playerWheat;
    int playerKnightCard;
    int playerVictoryCard;
    int playerRoadBuildCard;
    int playerMonopolyCard;
    int playerYearOfPlentyCard;
    public ResourcePane(){
//        this.setStyle("-fx-background-color: red;");
//        this.setPrefSize(150,150);
        InitVariables();
        ColumnConstraints c1 = new ColumnConstraints();
        ColumnConstraints c2 = new ColumnConstraints();
        ColumnConstraints c3 = new ColumnConstraints();
        ColumnConstraints c4 = new ColumnConstraints();
        RowConstraints r1 = new RowConstraints();
        RowConstraints r2 = new RowConstraints();
        r1.setPercentHeight(25);
        r2.setPercentHeight(75);
        this.getRowConstraints().addAll(r1,r2);
        VBox v1 = new VBox(10);
        VBox v2 = new VBox(10);
        VBox v3 = new VBox(10);
        VBox v4 = new VBox(10);
        c1.setPercentWidth(25);
        c2.setPercentWidth(25);
        c3.setPercentWidth(25);
        c4.setPercentWidth(25);
        this.getColumnConstraints().addAll(c1,c2,c3,c4);
        Image d = new Image("BlueIvyCatan/images/deck.png",160,160,true,true);
        ImageView deck = new ImageView(d);

        GridPane diceGrid = new GridPane();
        ColumnConstraints diceColumn1 = new ColumnConstraints();
        ColumnConstraints diceColumn2 = new ColumnConstraints();
        RowConstraints diceRow1 = new RowConstraints();
        RowConstraints diceRow2 = new RowConstraints();
        diceColumn1.setPercentWidth(50);
        diceColumn2.setPercentWidth(50);
        diceRow1.setPercentHeight(50);
        diceRow2.setPercentHeight(50);
        diceGrid.getRowConstraints().addAll(diceRow1, diceRow2);
        diceGrid.getColumnConstraints().addAll(diceColumn1, diceColumn2);

        Image defaultDie = new Image("BlueIvyCatan/images/defaultdieface.png", 80,80,true,false);
        ImageView defaultDieFace1 = new ImageView(defaultDie);
        ImageView defaultDieFace2 = new ImageView(defaultDie);
        StackPane diePane1 = new StackPane();
        diePane1.getChildren().add(defaultDieFace1);
        diceGrid.add(diePane1,0,0);

        StackPane diePane2 = new StackPane();
        diePane2.getChildren().add(defaultDieFace2);
        diceGrid.add(diePane2,1,0);

        StackPane dieResultPane = new StackPane();
        Rectangle dieResultRect = new Rectangle(160,80);
        Text defaultDieResult = new Text("8");
        dieResultRect.setFill(Color.WHITE);
        dieResultPane.getChildren().addAll(dieResultRect,defaultDieResult);
        diceGrid.add(dieResultPane,0,1,2,1);
        this.add(diceGrid,0,0,2,1);




        this.add(deck,2,0,2,1);
        Image b = new Image("BlueIvyCatan/images/brick.jpg",80, 80, true, false);
        Image o = new Image("BlueIvyCatan/images/ore.jpg",80,80,true, false);
        Image wh = new Image("BlueIvyCatan/images/wheat.jpg", 80,80,true,false);
        Image s = new Image("BlueIvyCatan/images/sheep.jpg", 80,80,true,false);
        Image wo = new Image("BlueIvyCatan/images/wood.jpg", 80, 80, true, false);
        ImageView brick = new ImageView(b);
        ImageView ore = new ImageView(o);
        ImageView wood = new ImageView(wo);
        ImageView sheep = new ImageView(s);
        ImageView wheat = new ImageView(wh);
        v1.getChildren().addAll(brick, ore, wheat, sheep, wood);
        this.add(v1,0,1);

        ArrayList<Text> numResources = new ArrayList<>();
        Text numBrick = new Text(Integer.toString(playerBrick));
        numResources.add(numBrick);
        Text numOre = new Text(Integer.toString(playerOre));
        numResources.add(numOre);
        Text numWheat = new Text(Integer.toString(playerWheat));
        numResources.add(numWheat);
        Text numSheep = new Text(Integer.toString(playerSheep));
        numResources.add(numSheep);
        Text numWood = new Text(Integer.toString(playerWood));
        numResources.add(numWood);
        for (int i = 0; i<numResources.size();i++){
            StackPane stackPane = new StackPane();
            Rectangle rect = new Rectangle(80,80);
            Text text = numResources.get(i);
            rect.setFill(Color.WHITE);
            stackPane.getChildren().addAll(rect, text);
            v2.getChildren().add(stackPane);


        }
        this.add(v2,1,1);

        Image vp = new Image("BlueIvyCatan/images/victorypointcard.jpg",80,80,false,true);
        Image kn = new Image("BlueIvyCatan/images/knight.jpg",80,80,false,true);
        Image rb = new Image("BlueIvyCatan/images/roadbuilding.jpg", 80,80,false,true);
        Image mo = new Image("BlueIvyCatan/images/monopoly.jpg",80,80,false,true);
        Image yop = new Image("BlueIvyCatan/images/yearofplenty.jpg",80,80,false,true);
        ImageView knights= new ImageView(kn);
        ImageView victoryPointCards = new ImageView(vp);
        ImageView roadBuilding = new ImageView(rb);
        ImageView monopoly = new ImageView(mo);
        ImageView yearOfPlenty = new ImageView(yop);
        v3.getChildren().addAll(knights, victoryPointCards, roadBuilding, monopoly, yearOfPlenty);
        this.add(v3,2,1);

        ArrayList<Text> numCards = new ArrayList<>();
        Text numKnight = new Text(Integer.toString(playerKnightCard));
        numCards.add(numKnight);
        Text numVPCard = new Text(Integer.toString(playerVictoryCard));
        numCards.add(numVPCard);
        Text numRoadBuilding = new Text(Integer.toString(playerRoadBuildCard));
        numCards.add(numRoadBuilding);
        Text numMonopoly = new Text(Integer.toString(playerMonopolyCard));
        numCards.add(numMonopoly);
        Text numYearOfPlenty = new Text(Integer.toString(playerYearOfPlentyCard));
        numCards.add(numYearOfPlenty);
        for (int i = 0 ; i <numCards.size(); i++){
            StackPane cardStackPane = new StackPane();
            Rectangle cardRect = new Rectangle(80,80);
            Text cardText = numCards.get(i);
            cardRect.setFill(Color.WHITE);
            cardStackPane.getChildren().addAll(cardRect,cardText);
            v4.getChildren().add(cardStackPane);
        }
        this.add(v4,3,1);

    }

    void InitVariables(){
        playerWood = 0;
        playerBrick = 0;
        playerOre = 0;
        playerSheep = 0;
        playerWheat = 0;
        playerKnightCard = 0;
        playerMonopolyCard = 0;
        playerRoadBuildCard = 0;
        playerVictoryCard = 0;
        playerYearOfPlentyCard = 0;
    }
}
