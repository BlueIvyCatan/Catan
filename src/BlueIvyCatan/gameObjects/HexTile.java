package BlueIvyCatan.gameObjects;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.ArrayList;

/**
 * Created by Tyler on 2/22/2017.
 */
public class HexTile extends StackPane {
    double xCenter;
    double yCenter;
    String hexID;
    int integerId;
    String hexType;
    double hexRadius;
    int hexRoll;
    Polygon hex = new Polygon();
    ArrayList<Integer> connectedCities = new ArrayList<>();
    public HexTile(double radius, double centerX, double centerY, int id){
        xCenter = centerX;
        yCenter = centerY;
        integerId = id;
        double hexRadius = radius;
        ArrayList<Double> xyCoords = new ArrayList<Double>();
        for (int i = 0; i<6; i++){
            double x = radius * Math.cos(i*Math.PI/3)+ xCenter;
            xyCoords.add(x);
            double y = yCenter - radius * Math.sin(i*Math.PI/3);
            xyCoords.add(y);
        }
        hex.getPoints().addAll(xyCoords);
        hex.setFill(Color.WHITE);
        hex.setStroke(Color.BLACK);
        hexID = "Hex";
        if (id<10){
            hexID +="0";
        }
        hexID += Integer.toString(id);
        this.setId(hexID);
        this.getChildren().add(hex);
        this.setTranslateX(xCenter);
//        this.setClip(hex);
        this.setTranslateY(yCenter);
//        Tooltip.install(this,new Tooltip("Connected Cities: " + ));



    }

    public double getCenterX(){
        return xCenter;
    }

    public double getCenterY(){
        return yCenter;
    }

    public void setHexType(String type){
        hexType = type;
        Image tileBackground;
        switch(hexType) {
            case "forest":
                tileBackground = new Image("BlueIvyCatan/images/Forest Tile.PNG", hexRadius, hexRadius, true, true);
                break;
            case "field":
                tileBackground = new Image("BlueIvyCatan/images/FieldsTile.PNG", hexRadius, hexRadius, true, true);
                break;
            case "pasture":
                tileBackground = new Image("BlueIvyCatan/images/Pasture Tile.PNG", hexRadius, hexRadius, true, true);
                break;
            case "mountain":
                tileBackground = new Image("BlueIvyCatan/images/Mountains Tile.PNG", hexRadius, hexRadius, true, true);
                break;
            case "hill":
                tileBackground = new Image("BlueIvyCatan/images/Hills Tile.PNG", hexRadius, hexRadius, true, true);
                break;
            case "desert":
                tileBackground = new Image("BlueIvyCatan/images/Desert Tile.PNG", hexRadius, hexRadius, true, true);
                break;
            default:
                tileBackground = new Image("BlueIvyCatan/images/deck.png", hexRadius, hexRadius,true,true);

        }
        ImagePattern background =new ImagePattern(tileBackground, 0 ,0,1,1,true);
        hex.setFill(background);
//        ImageView background = new ImageView(tileBackground);
//        background.setRotate(30);
//        this.setClip(background);
    }

    public String getHexType(){ return hexType;}

    public void setConnectedCities(ArrayList<Integer> cities){
        connectedCities = cities;
        String tooltipText = "Connected Cities: ";
        for (int i = 0; i < connectedCities.size(); i++){
            tooltipText += Integer.toString(connectedCities.get(i));
            tooltipText += " ";
        }
        Tooltip.install(this, new Tooltip(tooltipText));

    }

    public void setHexRoll(Integer roll, double cityRadius){
        hexRoll = roll;
        StackPane pane = new StackPane();
        Circle circle = new Circle(this.getCenterX(),this.getCenterY(), cityRadius);
        circle.setFill(Color.WHITE);
        pane.getChildren().add(circle);

        if (roll>0){
            Text text = new Text(Integer.toString(roll));
            text.setBoundsType(TextBoundsType.VISUAL);
            pane.getChildren().add(text);
        } else {
            ImagePattern robber = new ImagePattern(new Image("BlueIvyCatan/images/pawn.png"));
            circle.setFill(robber);
        }
        this.getChildren().add(pane);
    }

    public Integer getHexRoll(){return hexRoll;}
}
