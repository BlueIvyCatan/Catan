package BlueIvyCatan.gameObjects;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 3/29/2017.
 */
public class Player {
    String playerName;
    int playerVP;
    int playerOre;
    int playerWood;
    int playerSheep;
    int playerBrick;
    int playerWheat;
    int playerKnightCard;
    int playerVPCard;
    int playerRoadBuildingCard;
    int playerMonopolyCard;
    int playerYearOfPlentyCard;
    Paint playerColor;
    ArrayList<ArrayList<Integer>> playerCities = new ArrayList<ArrayList<Integer>>();
    List<Integer> playerRoads = new ArrayList<Integer>();


    public void Player(){

    }

    public void setPlayerName(String name){
        playerName = name;
    }

    public void setPlayerVP(int vp){
        playerVP = vp;
    }

    public int getPlayerVP(){
        return playerVP;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void setPlayerOre(int ore){
        playerOre = ore;
    }

    public int getPlayerOre(){
        return playerOre;
    }

    public void setPlayerWood(int wood){
        playerWood = wood;
    }

    public int getPlayerWood(){
        return playerWood;
    }

    public void setPlayerSheep(int sheep){
        playerSheep = sheep;
    }

    public int getPlayerSheep(){
        return playerSheep;
    }

    public void setPlayerBrick(int brick){
        playerBrick = brick;
    }

    public int getPlayerBrick(){
        return playerBrick;
    }

    public void setPlayerWheat(int wheat){
        playerWheat = wheat;
    }

    public int getPlayerWheat(){
        return playerWheat;
    }

    public void setPlayerKnightCard(int knight){
        playerKnightCard = knight;
    }

    public int getPlayerKnightCard(){
        return playerKnightCard;
    }

    public void setPlayerVPCard(int vpCard){
        playerVPCard = vpCard;
    }

    public int getPlayerVPCard(){
        return playerVPCard;
    }

    public void setPlayerRoadBuildingCard(int roadBuildingCard){
        playerRoadBuildingCard = roadBuildingCard;
    }

    public int getPlayerRoadBuildingCard(){
        return playerRoadBuildingCard;
    }

    public void setPlayerMonopolyCard(int monopolyCard){
        playerMonopolyCard = monopolyCard;
    }

    public int getPlayerMonopolyCard(){
        return playerMonopolyCard;
    }

    public void setPlayerYearOfPlentyCard(int yearOfPlentyCard){
        playerYearOfPlentyCard = yearOfPlentyCard;
    }

    public int getPlayerYearOfPlentyCard(){
        return playerYearOfPlentyCard;
    }

    public void addPlayerCity(ArrayList<Integer> cityInfo){
        playerCities.add(cityInfo);

    }

    public void addPlayerRoad(int id) {playerRoads.add(id);}

    public void setPlayerColor(Paint color) {playerColor = color;}

    public void addPlayerResource(String resource, int number){
        switch(resource)
        {
            case "forest":
                this.setPlayerWood(getPlayerWood()+number);
                break;
            case "field":
                this.setPlayerWheat(getPlayerWheat()+number);
                break;
            case "pasture":
                this.setPlayerSheep(getPlayerSheep()+number);
                break;
            case "mountain":
                this.setPlayerOre(getPlayerOre()+number);
                break;
            case "hill":
                this.setPlayerBrick(getPlayerBrick()+number);
                break;
            default:
                break;
        }
    }

    public void updateVP(){
        playerVP = 0;
        for (int i = 0; i < playerCities.size(); i++){
            playerVP += playerCities.get(i).get(1);
        }
        playerVP += playerVPCard;

    }
}
