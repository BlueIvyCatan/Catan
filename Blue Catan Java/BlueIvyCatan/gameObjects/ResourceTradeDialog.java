package gameObjects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by Tyler on 4/25/2017.
 */
public class ResourceTradeDialog extends Dialog {
    String resourceToGain;
    ArrayList<String> resourcesToTrade;
    GridPane grid;
    ArrayList<ChoiceBox> selectedResourceAmounts;
    Player currentPlayer;
    int brick;
    int ore;
    int wheat;
    int wood;
    int sheep;
    ResourceTradeDialog dialog;
    ChoiceBox currentChoiceBox;
    Number newChoiceValue;

    public ResourceTradeDialog(String resource, Player player){
        dialog = this;

        currentPlayer = player;
        resourceToGain = resource;
        brick = currentPlayer.getPlayerBrick();
        ore = currentPlayer.getPlayerOre();
        wheat = currentPlayer.getPlayerWheat();
        wood = currentPlayer.getPlayerWood();
        sheep = currentPlayer.getPlayerSheep();
        this.setTitle("Trade Resources");
        this.setHeaderText("You are trading for: "+ resource +".\n" +"Select a total of exactly four other resources and press the 'Okay' button to complete the trade.\n Press 'Cancel' to cancel the trade");
        this.setResizable(false);
        setUpResourceList();
        setUpGrid();
        this.getDialogPane().setContent(grid);
        this.getDialogPane().getButtonTypes().add(ButtonType.OK);
        this.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        checkSelectionTotal();



        this.setResultConverter(new Callback<ButtonType, Integer>()  {
            @Override
            public Integer call(ButtonType b) {

                if (b == ButtonType.OK) {

                    return getTradeResult();


                } else if (b == ButtonType.CANCEL){
                    return null;
                }
                return null;
            }
        });
    }

    private void setUpResourceList(){
        resourcesToTrade = new ArrayList<>();
        resourcesToTrade.add("brick");
        resourcesToTrade.add("ore");
        resourcesToTrade.add("wheat");
        resourcesToTrade.add("sheep");
        resourcesToTrade.add("wood");
        switch(resourceToGain){
            case "wood":
                resourcesToTrade.remove(5);
                break;
            case "brick":
                resourcesToTrade.remove(0);
                break;
            case "ore":
                resourcesToTrade.remove(1);
                break;
            case "sheep":
                resourcesToTrade.remove(3);
                break;
            case "wheat":
                resourcesToTrade.remove(2);
                break;
        }
    }

    private void setUpGrid(){
        grid = new GridPane();
        for (int i = 0; i<resourcesToTrade.size(); i++){
            Label label = new Label(resourcesToTrade.get(i)+":");
            grid.add(label,i,0);
        }
        selectedResourceAmounts = new ArrayList<>();
        for (int i = 0; i<resourcesToTrade.size(); i++){
            ChoiceBox cb = new ChoiceBox();
            int resourceMax = 0;
            switch(resourcesToTrade.get(i)){
                case "wood":
                    resourceMax = wood;
                    break;
                case "brick":
                    resourceMax = brick;
                    break;
                case "ore":
                    resourceMax = ore;
                    break;
                case "sheep":
                    resourceMax = sheep;
                    break;
                case "wheat":
                    resourceMax = wheat;
                    break;
            }
            if (resourceMax>0){
                for (int k = 0; k<resourceMax; k++) {
                    cb.getItems().add(k + 1);

                }
            } else {
                cb.getItems().add(0);
            }
            cb.getSelectionModel().selectFirst();
            cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    currentChoiceBox = cb;
                    newChoiceValue = newValue;
                    checkSelectionTotal();
                }
            });
            grid.add(cb,i,1);
            selectedResourceAmounts.add(cb);

        }

    }

    private Integer getTradeResult(){
        int newValue = 0;
        switch(resourceToGain){
            case "wood":
                currentPlayer.setPlayerWood(currentPlayer.getPlayerWood()+1);
                newValue = currentPlayer.getPlayerWood();
                break;
            case "brick":
                currentPlayer.setPlayerBrick(currentPlayer.getPlayerBrick()+1);
                newValue = currentPlayer.getPlayerBrick();
                break;
            case "ore":
                currentPlayer.setPlayerOre(currentPlayer.getPlayerOre()+1);
                newValue = currentPlayer.getPlayerOre();
                break;
            case "sheep":
                currentPlayer.setPlayerSheep(currentPlayer.getPlayerSheep()+1);
                newValue = currentPlayer.getPlayerSheep();
                break;
            case "wheat":
                currentPlayer.setPlayerWheat(currentPlayer.getPlayerWheat()+1);
                newValue = currentPlayer.getPlayerWheat();
                break;

        }
        for (int i = 0; i <resourcesToTrade.size();i++){
            switch(resourcesToTrade.get(i)){
                case "wood":
                    if(currentPlayer.getPlayerWood()==0){
                        break;
                    }
                    currentPlayer.setPlayerWood(currentPlayer.getPlayerWood()-(int)selectedResourceAmounts.get(i).getValue());
                    break;
                case "brick":
                    if(currentPlayer.getPlayerBrick()==0){
                        break;
                    }
                    currentPlayer.setPlayerBrick(currentPlayer.getPlayerBrick()-(int)selectedResourceAmounts.get(i).getValue());
                    break;
                case "ore":
                    if(currentPlayer.getPlayerOre()==0){
                        break;
                    }
                    currentPlayer.setPlayerOre(currentPlayer.getPlayerOre()-(int)selectedResourceAmounts.get(i).getValue());
                    break;
                case "sheep":
                    if(currentPlayer.getPlayerSheep()==0){
                        break;
                    }
                    currentPlayer.setPlayerSheep(currentPlayer.getPlayerSheep()-(int)selectedResourceAmounts.get(i).getValue());
                    break;
                case "wheat":
                    if(currentPlayer.getPlayerWheat()==0){
                        break;
                    }
                    currentPlayer.setPlayerWheat(currentPlayer.getPlayerWheat()-(int)selectedResourceAmounts.get(i).getValue());
                    break;

            }
        }
        return newValue;



    }

    private void checkSelectionTotal(){
        int total = 0;
        for(int i = 0; i<selectedResourceAmounts.size();i++){
            if (selectedResourceAmounts.get(i)==currentChoiceBox){
                total += newChoiceValue.intValue()+1;
            } else {

                total += (int) selectedResourceAmounts.get(i).getValue();
            }

        }
        if (total == 4) {
            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        } else {
            dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

        }
    }
}
