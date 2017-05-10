package gameObjects;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by Tyler on 3/29/2017.
 */
public class Dice {
    Random rand = new Random();
    int die1;
    int die2;
    int dieResult;
    Pane die1Face;
    Pane die2Face;
    double dieHeight;
    double dieWidth;
    final Image FACE1 = new Image("images/dice-six-faces-one.png");
    final Image FACE2 = new Image("images/dice-six-faces-two.png");
    final Image FACE3 = new Image("images/dice-six-faces-three.png");
    final Image FACE4 = new Image("images/dice-six-faces-four.png");
    final Image FACE5 = new Image("images/dice-six-faces-five.png");
    final Image FACE6 = new Image("images/dice-six-faces-six.png");

    public Dice(){
        initializeValues();


    }

    public void initializeValues(){
        die1 = 0;
        die2 = 0;
        dieResult = 0;
        die1Face = new Pane();
        die2Face = new Pane();
        dieWidth=80;
        dieHeight=80;
    }

    public void rollDice(){
        die1 = rand.nextInt(6) +1;
        setDieFace("die1");
        die2 = rand.nextInt(6) +1;
        setDieFace("die2");
        dieResult = die1 + die2;
    }

    public void setDieFace(String die){
        Pane currentDie = die1Face;
        int result = die1;

        if (die.equals("die2")){
            currentDie = die2Face;
            result = die2;

        }
        ImageView imageView = new ImageView();
        imageView.setFitHeight(dieHeight);
        imageView.setFitWidth(dieWidth);
        switch(result){
            case 1: imageView.setImage(FACE1);
                break;
            case 2: imageView.setImage(FACE2);
                break;
            case 3: imageView.setImage(FACE3);
                break;
            case 4: imageView.setImage(FACE4);
                break;
            case 5: imageView.setImage(FACE5);
                break;
            case 6: imageView.setImage(FACE6);
                break;
            default: break;
        }
        currentDie.getChildren().add(imageView);

    }
    public Pane getDie1Face(){return die1Face;}
    public Pane getDie2Face(){return die2Face;}
    public int getDiceResult() {return dieResult;}

    public void setDieHeight(double height){
        dieHeight=height;


    }

    public void setDieWidth(double width){
        dieWidth=width;
    }


}
