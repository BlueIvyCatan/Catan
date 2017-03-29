package BlueIvyCatan;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Tyler on 3/29/2017.
 */
public class City extends Pane {
    String owner;
    boolean owned;
    Circle clip = new Circle();
    double cityRadius;

    public City(double x, double y, double radius){
        owned = false;
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
        Image s = new Image("BlueIvyCatan/settlement.jpg",cityRadius, cityRadius, true, false);
        ImageView settlement = new ImageView(s);
        this.getChildren().remove(clip);
        settlement.setClip(clip);

        this.getChildren().add(settlement);
    }

    public String getOwner(){
        return owner;
    }

    public boolean isOwned(){
        return owned;
    }
}
