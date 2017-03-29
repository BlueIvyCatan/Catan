package BlueIvyCatan;

/**
 * Created by Tyler on 3/29/2017.
 */
public class Game {
    double screenWidth;
    double screenHeight;

    public void Game(){
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        Player player4 = new Player();

    }
    
    public void setScreenWidth(double width){
        screenWidth = width;
        
    }
    
    public double getScreenWidth(){
        return screenWidth;
    }
    
    public void setScreenHeight(double height){
        screenHeight = height;
    }
    
    public double getScreenHeight(){
        return screenHeight;
    }
}
