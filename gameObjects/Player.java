package gameObjects;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tyler on 3/29/2017.
 */
public class Player {
    String playerName;
    String playerPassword;
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
    int playerPlayedVPCard;
    int playerHandSize;
    Paint playerColor;
    Image playerSettlement;
    Image playerCity;
    Image playerIcon;
    ArrayList<ArrayList<Integer>> playerCities = new ArrayList<ArrayList<Integer>>();
    List<Road> playerRoads = new ArrayList<>();
    int playerKnights;
    boolean largestArmy;


    public Player(){
        initVariables();

    }

    public void setPlayerSettlement(Image image){playerSettlement = image;}
    public void setPlayerCity (Image image){playerCity = image;}
    public void setPlayerIcon (Image image){playerIcon = image;}

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

    public void setPlayerPassword(String password) {
        if (password == null){
            playerPassword = "none";
        } else {
            playerPassword = password;
        }
    }

    public String getPlayerPassword() {return playerPassword;}

    public void setPlayerOre(int ore){
        playerOre = ore;
        updateHandSize();
    }

    public int getPlayerOre(){
        return playerOre;
    }

    public void setPlayerWood(int wood){
        playerWood = wood;
        updateHandSize();
    }

    public int getPlayerWood(){
        return playerWood;
    }

    public void setPlayerSheep(int sheep){
        playerSheep = sheep;
        updateHandSize();
    }

    public int getPlayerSheep(){
        return playerSheep;
    }

    public void setPlayerBrick(int brick){
        playerBrick = brick;
        updateHandSize();
    }

    public int getPlayerBrick(){
        return playerBrick;
    }

    public void setPlayerWheat(int wheat){
        playerWheat = wheat;
        updateHandSize();
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

    public void incrementPlayedVPCard(){playerPlayedVPCard++;}

    public void addPlayerCity(ArrayList<Integer> cityInfo){
        playerCities.add(cityInfo);

    }

    private void initVariables(){
        playerVP=0;
        playerBrick=0;
        playerOre=0;
        playerWood=0;
        playerSheep=0;
        playerWheat=0;
        playerKnightCard=0;
        playerVPCard=0;
        playerRoadBuildingCard=0;
        playerMonopolyCard=0;
        playerYearOfPlentyCard=0;
        playerPlayedVPCard=0;
        playerHandSize = 0;
        playerKnights = 0;
        largestArmy=false;
    }

    public void addPlayerRoad(Road road) {playerRoads.add(road);}

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
        updateHandSize();
    }

    public void updateVP(){
        playerVP = 0;
        for (int i = 0; i < playerCities.size(); i++){
            playerVP += playerCities.get(i).get(1);
        }
        playerVP += playerPlayedVPCard;
        if(largestArmy){
            playerVP+=2;
        }

    }

    public void incrementPlayerKnights(){playerKnights++;}
    public int getPlayerKnights(){return playerKnights;}
    public void setLargestArmy(boolean set){largestArmy=set;}

    private void updateHandSize(){
        playerHandSize = playerBrick+playerWheat+playerWood+playerOre+playerSheep;
    }
}
