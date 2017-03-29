package BlueIvyCatan;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.util.ArrayList;

/**
 * Created by Tyler on 2/22/2017.
 */
public class HexTile extends Polygon {
    double xCenter;
    double yCenter;
    String hexID;
    public HexTile(double radius, double centerX, double centerY, int id){
        xCenter = centerX;
        yCenter = centerY;
        ArrayList<Double> xyCoords = new ArrayList<Double>();
        for (int i = 0; i<6; i++){
            double x = radius * Math.cos(i*Math.PI/3)+ xCenter;
            xyCoords.add(x);
            double y = yCenter - radius * Math.sin(i*Math.PI/3);
            xyCoords.add(y);
        }
        this.getPoints().addAll(xyCoords);
        this.setFill(Color.WHITE);
        this.setStroke(Color.BLACK);
        hexID = "Hex";
        if (id<10){
            hexID +="0";
        }
        hexID += Integer.toString(id);
        this.setId(hexID);
        Tooltip.install(this,new Tooltip(hexID));



    }

    public double getCenterX(){
        return xCenter;
    }

    public double getCenterY(){
        return yCenter;
    }
}
