package BlueIvyCatan;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.util.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Main extends Application {
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
    ArrayList<HexTile> hexTiles = new ArrayList<HexTile>();
    ArrayList<Circle> cityList = new ArrayList<Circle>();
    ArrayList<Rectangle> roadList = new ArrayList<Rectangle>();
    ArrayList<Double> hexCenter = new ArrayList<Double>();
    ArrayList<Double> roadCenter = new ArrayList<Double>();
    ArrayList<Double> roadAngleList = new ArrayList<Double>();
    ArrayList<ArrayList<Double>> hexCenterList = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> cityCenterList = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> roadCenterList = new ArrayList<ArrayList<Double>>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        initVariables();
        Game game = new Game();
//
//        centerTile();
//        middleCircle();
//        Group root = new Group();
        GameScreen gameScreen = new GameScreen();

        gameScreen.initializeGameScreen();
//        gameScreen.setGridLinesVisible(true);
        Scene scene = new Scene(gameScreen);
//        outerCircle();
//        generateCities();
//        addCities();
//        generateRoads();
//        addRoads();
//
//        root.getChildren().addAll(hexTiles);
//        root.getChildren().addAll(cityList);
//        root.getChildren().addAll(roadList);


        primaryStage.setScene(scene);
//        primaryStage.setMaximized(true);
        primaryStage.setTitle("Blue Ivy Catan");
        primaryStage.show();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth()*.9;
        screenHeight = screenSize.getHeight()*.9;
        game.setScreenHeight(screenHeight);
        game.setScreenWidth(screenWidth);
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void initVariables() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = screenSize.getWidth();
        screenHeight = screenSize.getHeight();


        if (screenHeight > screenWidth) {
            apo = screenWidth / 15;
        } else {
            apo = screenHeight / 15;
        }


        radius = apo * (2 / Math.sqrt(3));
        cityRadius = radius / 5;
        roadLength = radius - 2 * cityRadius;
        roadHeight = cityRadius / 2;
        roadRadius = Math.sqrt(Math.pow(roadLength / 2, 2) + Math.pow(roadHeight / 2, 2));

    }
}

//    public void cleanupApplication () {
//
//    }

//    public void centerTile() {
//        hexTiles.add(new HexTile(radius, screenWidth/2, screenHeight/2));
//        hexX = screenWidth/2;
//        hexY = screenHeight/2;
//        hexCenterList.add(generateCenter(hexX, hexY));
//
//
//    }
//
//    public void middleCircle() {
//        for(int i=0; i<6; i++){
//            double newX = Math.round(apo*2*Math.cos(Math.PI/6+i*Math.PI/3)+hexTiles.get(0).getCenterX());
//            double newY = Math.round(hexTiles.get(0).getCenterY()-apo*2*Math.sin(Math.PI/6+i*Math.PI/3));
//            hexCenterList.add(generateCenter(newX, newY));
//
//            hexTiles.add(new HexTile(radius, newX, newY));
//
//
//        }
//    }

//    public ArrayList<Double> generateCenter(double x, double y){
//        ArrayList<Double> xy = new ArrayList<Double>();
//        xy.add(x);
//        xy.add(y);
//        return xy;
//
//    }
//    public void generateCities(){
//        for(int i = 0; i<hexCenterList.size(); i++){
//            for(int k = 0; k<6; k++){
//                double newX = Math.round(radius*Math.cos(k*Math.PI/3)+hexTiles.get(i).getCenterX());
//                double newY = Math.round(hexTiles.get(i).getCenterY()-radius*Math.sin(k*Math.PI/3));
//                boolean cityAdd=true;
//                for(int j=0; j<cityCenterList.size(); j++){
//                    if(cityCenterList.get(j).containsAll(generateCenter(newX, newY))){
//                        cityAdd=false;
//                    }
//
//                }
//                if(cityAdd){
//                    cityCenterList.add(generateCenter(newX, newY));
//
//                }
//
//
//
//            }
//
//        }
//
//    }
//
//    public void generateRoads(){
//        for(int i = 0; i<hexCenterList.size(); i++){
//            for(int k = 0; k<6; k++){
//                double roadAngle =(Math.PI/6+k*Math.PI/3+Math.PI/2);
//                double newX = Math.ceil(apo*Math.cos(Math.PI/6+k*Math.PI/3)+hexTiles.get(i).getCenterX());
//                double newY = Math.ceil(hexTiles.get(i).getCenterY()-apo*Math.sin(Math.PI/6+k*Math.PI/3));
//                boolean roadAdd=true;
//                for(int j=0; j<roadCenterList.size(); j++){
//                    if(roadCenterList.get(j).equals(generateCenter(newX, newY))){
//                        roadAdd=false;
//                    }
//
//                }
//                if(roadAdd){
//                    roadCenterList.add(generateCenter(newX, newY));
//                    roadAngleList.add(Math.toDegrees(roadAngle));
//
//                }
//
//
//
//            }
//
//        }
//
//    }
//
//    public void addCities() {
//        for(int i = 0; i<cityCenterList.size(); i++){
//            double cityX = cityCenterList.get(i).get(0);
//            double cityY = cityCenterList.get(i).get(1);
//            Circle circle = new Circle(cityX, cityY, cityRadius);
//            circle.setFill(Color.CYAN);
//            cityList.add(circle);
//        }
//    }
//
//    public void addRoads() {
//        for(int i = 0; i<roadCenterList.size(); i++){
//            double roadCenterX= roadCenterList.get(i).get(0);
//            double roadCenterY= roadCenterList.get(i).get(1);
//            double roadX = Math.round(roadCenterX+roadRadius*Math.cos(Math.toRadians(roadAngleList.get(i))-Math.asin((roadHeight/2)/roadRadius)));
//            double roadY = Math.round(roadCenterY-roadRadius*Math.sin(Math.toRadians(roadAngleList.get(i))-Math.asin((roadHeight/2)/roadRadius)));
//            roadList.add(new Rectangle(roadX, roadY, roadLength, roadHeight));
//            roadList.get(i).getTransforms().add(new Rotate(180-roadAngleList.get(i), roadX, roadY));
//            roadList.get(i).setFill(Color.GREEN);
//
//
//
//
//        }
//    }
//
//    public void outerCircle() {
//        for(int i = 1; i<7; i++){
//            double middleX = hexTiles.get(i).getCenterX();
//            double middleY = hexTiles.get(i).getCenterY();
//            for(int k = 0; k<6; k++){
//                double newX = Math.round(apo*2*Math.cos(Math.PI/6+k*Math.PI/3)+middleX);
//                double newY = Math.round(middleY-apo*2*Math.sin(Math.PI/6+k*Math.PI/3));
//                boolean centerNew = true;
//                for(int j = 0; j<hexCenterList.size();j++){
//                    if(hexCenterList.get(j).containsAll(generateCenter(newX, newY))){
//                        centerNew = false;
//
//                    }
//                }
//                if(centerNew){
////                    if(hexCenterList.size()>20) {
////                        scene.setFill(Color.RED);
////
////                    }
//                    hexCenterList.add(generateCenter(newX, newY));
//                    hexTiles.add(new HexTile(radius,newX, newY));
//                }
//                hexCenter.clear();
//
//            }
//        }
//    }
//}
//
