package BlueIvyCatan.gameObjects;

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
    double paneHeight;
    double paneWidth;
    private final int NUMCOLUMNS = 4;
    private final int NUMROWS = 4;
    double columnWidth;
    double rowHeight;
    double diceRowHeight;
    double resourceRowHeight;
    Dice dice;
    GridPane diceGrid;
    Pane diePane1;
    Pane diePane2;
    Text dieResult;
    VBox v1;
    VBox v2;
    VBox v3;
    VBox v4;

    public ResourcePane(double height, double width){
//        this.setStyle("-fx-background-color: red;");
//        this.setPrefSize(150,150);
        paneHeight = height;
        paneWidth = width;
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
        v1 = new VBox(0);
        v2 = new VBox(0);
        v3 = new VBox(0);
        v4 = new VBox(0);
        c1.setPercentWidth(100/ NUMCOLUMNS);
        c2.setPercentWidth(100/ NUMCOLUMNS);
        c3.setPercentWidth(100/ NUMCOLUMNS);
        c4.setPercentWidth(100/ NUMCOLUMNS);
        this.getColumnConstraints().addAll(c1,c2,c3,c4);
        Image d = new Image("BlueIvyCatan/images/deck.png",columnWidth*2,diceRowHeight,false,true);
        ImageView deck = new ImageView(d);

        diceGrid = new GridPane();
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

        Image defaultDie = new Image("BlueIvyCatan/images/defaultdieface.png", columnWidth,diceRowHeight/2,false,false);
        ImageView defaultDieFace1 = new ImageView(defaultDie);
        ImageView defaultDieFace2 = new ImageView(defaultDie);
        diePane1 = new StackPane();
        diePane1.getChildren().add(defaultDieFace1);
        diceGrid.add(diePane1,0,0);

        diePane2 = new StackPane();
        diePane2.getChildren().add(defaultDieFace2);
        diceGrid.add(diePane2,1,0);

        StackPane dieResultPane = new StackPane();
        Rectangle dieResultRect = new Rectangle(columnWidth*2,diceRowHeight/2);
        dieResult = new Text("8");
        dieResultRect.setFill(Color.WHITE);
        dieResultPane.getChildren().addAll(dieResultRect,dieResult);
        diceGrid.add(dieResultPane,0,1,2,1);
        this.add(diceGrid,0,0,2,1);




        this.add(deck,2,0,2,1);


        Image b = new Image("BlueIvyCatan/images/brick.jpg", columnWidth, resourceRowHeight, false, false);
        Image o = new Image("BlueIvyCatan/images/ore.jpg",columnWidth,resourceRowHeight,false, false);
        Image wh = new Image("BlueIvyCatan/images/wheat.jpg", columnWidth,resourceRowHeight,false,false);
        Image s = new Image("BlueIvyCatan/images/sheep.jpg", columnWidth,resourceRowHeight,false,false);
        Image wo = new Image("BlueIvyCatan/images/wood.jpg", columnWidth, resourceRowHeight, false, false);
        ImageView brick = new ImageView(b);
        ImageView ore = new ImageView(o);
        ImageView wood = new ImageView(wo);
        ImageView sheep = new ImageView(s);
        ImageView wheat = new ImageView(wh);
        v1.getChildren().addAll(brick, ore, wheat, sheep, wood);
        this.add(v1,0,1);

        updateResourceText(v2);


        this.add(v2,1,1);

        Image vp = new Image("BlueIvyCatan/images/victorypointcard.jpg",columnWidth,resourceRowHeight,false,true);
        Image kn = new Image("BlueIvyCatan/images/knight.jpg",columnWidth,resourceRowHeight,false,true);
        Image rb = new Image("BlueIvyCatan/images/roadbuilding.jpg", columnWidth,resourceRowHeight,false,true);
        Image mo = new Image("BlueIvyCatan/images/monopoly.jpg",columnWidth,resourceRowHeight,false,true);
        Image yop = new Image("BlueIvyCatan/images/yearofplenty.jpg",columnWidth,resourceRowHeight,false,true);
        ImageView knights= new ImageView(kn);
        ImageView victoryPointCards = new ImageView(vp);
        ImageView roadBuilding = new ImageView(rb);
        ImageView monopoly = new ImageView(mo);
        ImageView yearOfPlenty = new ImageView(yop);
        v3.getChildren().addAll(knights, victoryPointCards, roadBuilding, monopoly, yearOfPlenty);
        this.add(v3,2,1);

        updateCardText(v4);


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
        columnWidth = paneWidth / NUMCOLUMNS;
        rowHeight = paneHeight / NUMROWS;
        diceRowHeight = rowHeight;
        resourceRowHeight = rowHeight*3/5;

    }

    void setDiceParameters(){
        dice.setDieHeight(columnWidth);
        dice.setDieWidth(diceRowHeight/2);
        updateDice();

    }

    public void update(Player player){
        playerWood = player.getPlayerWood();
        playerBrick = player.getPlayerBrick();
        playerOre = player.getPlayerOre();
        playerSheep = player.getPlayerSheep();
        playerWheat = player.getPlayerWheat();
        playerKnightCard = player.getPlayerKnightCard();
        playerMonopolyCard = player.getPlayerMonopolyCard();
        playerRoadBuildCard = player.getPlayerRoadBuildingCard();
        playerVictoryCard = player.getPlayerVPCard();
        playerYearOfPlentyCard = player.getPlayerYearOfPlentyCard();
        updateResourceText(v2);
        updateCardText(v4);
    }

    private void updateResourceText(VBox vBox){
        vBox.getChildren().clear();
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
            Rectangle rect = new Rectangle(columnWidth,resourceRowHeight);
            Text text = numResources.get(i);
            rect.setFill(Color.WHITE);
            stackPane.getChildren().addAll(rect, text);
            vBox.getChildren().add(stackPane);


        }

    }

    private void updateCardText(VBox vBox){
        vBox.getChildren().clear();
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
            Rectangle cardRect = new Rectangle(columnWidth,resourceRowHeight);
            Text cardText = numCards.get(i);
            cardRect.setFill(Color.WHITE);
            cardStackPane.getChildren().addAll(cardRect,cardText);
            vBox.getChildren().add(cardStackPane);
        }

    }

    public void updateDice(){
        dice.rollDice();
        diePane1 = dice.getDie1Face();
        diePane2 = dice.getDie2Face();
        diceGrid.getChildren().clear();
        diceGrid.add(diePane1,0,0);
        diceGrid.add(diePane2, 1,0);
        dieResult.setText(Integer.toString(dice.getDiceResult()));
        StackPane dieResultPane = new StackPane();
        Rectangle dieResultRect = new Rectangle(columnWidth*2,diceRowHeight/2);
        dieResultRect.setFill(Color.WHITE);
        dieResultPane.getChildren().addAll(dieResultRect,dieResult);
        diceGrid.add(dieResultPane,0,1,2,1);

    }
}
