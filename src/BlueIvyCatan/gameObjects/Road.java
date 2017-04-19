package BlueIvyCatan.gameObjects;

import javafx.scene.shape.Rectangle;

/**
 * Created by Tyler on 4/18/2017.
 */
public class Road extends Rectangle {
    boolean owned;
    boolean available;
    Player owner;
    int roadId;
    public Road(double x, double y, double width, double height){
        super(x,y,width,height);
        owned = false;
        available = true;

    }
    public void setOwner(Player player){
        owner = player;
        this.setFill(player.playerColor);
        owned = true;
    }
    public Player getOwner(){return owner;}

    public void setRoadId(int id){roadId=id;}

    public int getRoadId(){return roadId;}

    public boolean isOwned(){return owned;}

}
