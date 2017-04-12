package BlueIvyCatan;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by Tyler on 3/29/2017.
 */
public class City extends Pane {
    String owner;
    boolean owned;
    Circle clip = new Circle();
    double cityRadius;
    int cityID;
    double centerX;
    double centerY;
    ArrayList<Integer> connectedRoads= new ArrayList<>();

    public City(double x, double y, double radius){
        owned = false;
        centerX = x;
        centerY = y;
        clip.setCenterX(x);
        clip.setCenterY(y);
        clip.setRadius(radius);
        clip.setFill(Color.AQUA);
//        this.setStyle("-fx-background-color: #00FFFF");
        this.getChildren().add(clip);
        cityRadius = radius;

    }

    public void setOwner(String player){
        owner = player;
        owned = true;
        Image s = new Image("BlueIvyCatan/images/settlement.jpg", cityRadius*2, cityRadius*2, true, true);
        ImageView settlement = new ImageView(s);
//        this.getChildren().remove(clip);
//        settlement.setClip(clip);
//        BackgroundImage settlementImage = new BackgroundImage(s, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//        this.setBackground(new Background(settlementImage));
        ImagePattern imagePattern = new ImagePattern(s, 0, 0, 1, 1, true);
        clip.setFill(imagePattern);


//        this.getChildren().add(settlement);
        owned = true;
    }

    public String getOwner(){
        return owner;
    }

    public boolean isOwned(){
        return owned;
    }

    public void setId(int id) {cityID = id;}

    public double getCenterX(){return centerX;}

    public double getCenterY(){return centerY;}

    public void addConnectedRoad(int roadID){
        connectedRoads.add(roadID);

    }

    public void setRoadToolTip(){
        String toolTipText = "Connected Roads: ";
        for (int i = 0; i<connectedRoads.size();i++){
            toolTipText += Integer.toString(connectedRoads.get(i));
            toolTipText += " ";
        }
        Tooltip.install(this, new Tooltip(toolTipText));
    }

}
