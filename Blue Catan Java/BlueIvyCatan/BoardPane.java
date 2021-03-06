package BlueIvyCatan;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Popup;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Tyler on 3/6/2017.
 */
public class BoardPane extends BorderPane {
    double screenWidth;
    double screenHeight;
    double radius;
    double apo;
    double hexX;
    double hexY;
    double cityRadius;
    double roadLength;
    double roadRadius;
    double roadHeight;
    int hexID = 1;
    int cityId = 1;
    ArrayList<HexTile> hexTiles = new ArrayList<HexTile>();
    ArrayList<City> cityList = new ArrayList<City>();
    ArrayList<Rectangle> roadList = new ArrayList<Rectangle>();
    ArrayList<Double> hexCenter = new ArrayList<Double>();
    ArrayList<Double> roadCenter = new ArrayList<Double>();
    ArrayList<Double> roadAngleList = new ArrayList<Double>();
    ArrayList<ArrayList<Double>> hexCenterList = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> cityCenterList = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> roadCenterList = new ArrayList<ArrayList<Double>>();
    public BoardPane(){
//        this.setStyle("-fx-background-color: blue;");
//        this.setPrefSize(150,150);
        initVariables();
        centerTile();
        middleCircle();
        outerCircle();
        generateCities();
        addCities();
        generateRoads();
        addRoads();

        this.getChildren().addAll(hexTiles);
        this.getChildren().addAll(cityList);
        this.getChildren().addAll(roadList);

    }
    public void initVariables() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth()*.9*.8;
        screenHeight = screenSize.getHeight()*.9;


        if (screenHeight>screenWidth){
            apo = screenWidth/12;
        } else {
            apo = screenHeight/12;
        }


        radius = apo*(2/Math.sqrt(3));
        cityRadius = radius/5;
        roadLength = radius - 2*cityRadius;
        roadHeight = cityRadius/2;
        roadRadius = Math.sqrt(Math.pow(roadLength/2, 2)+Math.pow(roadHeight/2, 2));

    }
    public void centerTile() {
        hexTiles.add(new HexTile(radius, screenWidth/2, screenHeight/2, hexID));
        hexID++;
        hexX = Math.floor(screenWidth/2);
        hexY = Math.floor(screenHeight/2);
        hexCenterList.add(generateCenter(hexX, hexY));


    }

    public void middleCircle() {
        for(int i=0; i<6; i++){
            double newX = Math.floor(apo*2*Math.cos(Math.PI/6+i*Math.PI/3)+hexTiles.get(0).getCenterX());
            double newY = Math.floor(hexTiles.get(0).getCenterY()-apo*2*Math.sin(Math.PI/6+i*Math.PI/3));
            hexCenterList.add(generateCenter(newX, newY));

            hexTiles.add(new HexTile(radius, newX, newY, hexID));
            hexID++;


        }
    }

    public ArrayList<Double> generateCenter(double x, double y){
        ArrayList<Double> xy = new ArrayList<Double>();
        xy.add(x);
        xy.add(y);
        return xy;

    }
    public void generateCities(){
        for(int i = 0; i<hexCenterList.size(); i++){
            for(int k = 0; k<6; k++){
                double newX = Math.floor(radius*Math.cos(k*Math.PI/3)+hexTiles.get(i).getCenterX());
                double newY = Math.floor(hexTiles.get(i).getCenterY()-radius*Math.sin(k*Math.PI/3));
                boolean cityAdd=true;
                for(int j=0; j<cityCenterList.size(); j++){
                    if((cityCenterList.get(j).get(0)<=newX+cityRadius/2&&cityCenterList.get(j).get(0)>newX-cityRadius/2)&&(cityCenterList.get(j).get(1)<newY+cityRadius/2&&cityCenterList.get(j).get(1)>newY-cityRadius/2)){
                        cityAdd=false;
                    }

                }
                if(cityAdd||i==0){
                    cityCenterList.add(generateCenter(newX, newY));

                }



            }

        }

    }

    public void generateRoads(){
        for(int i = 0; i<hexCenterList.size(); i++){
            for(int k = 0; k<6; k++){
                double roadAngle =(Math.PI/6+k*Math.PI/3+Math.PI/2);
                double newX = Math.floor(apo*Math.cos(Math.PI/6+k*Math.PI/3)+hexTiles.get(i).getCenterX());
                double newY = Math.floor(hexTiles.get(i).getCenterY()-apo*Math.sin(Math.PI/6+k*Math.PI/3));
                boolean roadAdd=true;
                for(int j=0; j<roadCenterList.size(); j++){
                    if((roadCenterList.get(j).get(0)<=newX+roadRadius/2&&roadCenterList.get(j).get(0)>newX-roadRadius/2)&&(roadCenterList.get(j).get(1)<newY+roadRadius/2&&roadCenterList.get(j).get(1)>newY-roadRadius/2)){
                        roadAdd=false;
                    }

                }
                if(roadAdd){
                    roadCenterList.add(generateCenter(newX, newY));
                    roadAngleList.add(Math.toDegrees(roadAngle));

                }



            }

        }

    }

    public void addCities() {

        for(int i = 0; i<cityCenterList.size(); i++){
            String ID = "city";
            double cityX = cityCenterList.get(i).get(0);
            double cityY = cityCenterList.get(i).get(1);
            City circle = new City (cityX, cityY, cityRadius);

            if (i+1<10){
                ID +="0";
            }
            ID += Integer.toString(i+1);
            cityId++;
            circle.setId(ID);
            circle.setClip(new Circle(cityX,cityY,cityRadius));
            Popup popup = new Popup();
            Bounds citySceneCoord = circle.localToScene(circle.getBoundsInLocal());
            double citySceneX = citySceneCoord.getMinX();
            double citySceneY = citySceneCoord.getMinY();
            popup.getContent().addAll(new Text("You clicked on "+ID));
            circle.setOnMouseClicked((new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
//                    if (popup.isShowing()) {
//                        popup.hide();
//                    } else {
//                        popup.show(circle, event.getScreenX(), event.getScreenY());
//                    }
                    if(circle.isOwned()){

                    } else {
                        circle.setOwner("defaultPlayer");
                    }

                }
            }));

            Tooltip.install(circle, new Tooltip(ID));
            cityList.add(circle);
        }
    }

    public void addRoads() {
        for(int i = 0; i<roadCenterList.size(); i++){
            String ID = "Road";
            if (i+1<10){
                ID+="0";
            }
            ID += Integer.toString(i);
            double roadCenterX= roadCenterList.get(i).get(0);
            double roadCenterY= roadCenterList.get(i).get(1);
            double roadX = Math.floor(roadCenterX+roadRadius*Math.cos(Math.toRadians(roadAngleList.get(i))-Math.asin((roadHeight/2)/roadRadius)));
            double roadY = Math.floor(roadCenterY-roadRadius*Math.sin(Math.toRadians(roadAngleList.get(i))-Math.asin((roadHeight/2)/roadRadius)));
            Rectangle road = new Rectangle(roadX, roadY, roadLength, roadHeight);
            road.setId(ID);
            Tooltip.install(road, new Tooltip(ID));

            roadList.add(road);
            roadList.get(i).getTransforms().add(new Rotate(180-roadAngleList.get(i), roadX, roadY));
            roadList.get(i).setFill(Color.BLACK);




        }
    }

    public void outerCircle() {
        for(int i = 1; i<7; i++){
            double middleX = hexTiles.get(i).getCenterX();
            double middleY = hexTiles.get(i).getCenterY();
            for(int k = 0; k<6; k++){
                double newX = Math.floor(apo*2*Math.cos(Math.PI/6+k*Math.PI/3)+middleX);
                double newY = Math.floor(middleY-apo*2*Math.sin(Math.PI/6+k*Math.PI/3));
                boolean centerNew = true;
                for(int j = 0; j<hexCenterList.size();j++){
                    if(((hexCenterList.get(j).get(0)<=newX+apo/2)&&(hexCenterList.get(j).get(0)>=newX-apo/2))&&((hexCenterList.get(j).get(1)<=newY+apo/2)&&(hexCenterList.get(j).get(1)>=newY-apo/2))){
                        centerNew = false;

                    }
                }
                if(centerNew){
//                    if(hexCenterList.size()>20) {
//                        scene.setFill(Color.RED);
//
//                    }
                    hexCenterList.add(generateCenter(newX, newY));
                    hexTiles.add(new HexTile(radius,newX, newY, hexID));
                    hexID++;
                }
                hexCenter.clear();

            }
        }
    }
}


