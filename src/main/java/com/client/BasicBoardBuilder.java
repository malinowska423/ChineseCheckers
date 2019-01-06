package com.client;

import javafx.animation.FadeTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.ArrayList;

public class BasicBoardBuilder extends BoardBuilder {
    private BoardCircle moveCircle = null;
    private String moveStyle;
    private ArrayList<BoardCircle> pawnsList = new ArrayList<>();
    private Hexagon[][] hexagons;

    private String defHexStyle;
    private String markedHexStyle;

    private double xinit;
    private double xgap;
    private double ygap;


    @Override
    public void buildBoard(int playerId, String s, Pane pane, String [] pawnsColors) {

//        System.out.println(s);
        double inset = 10;

        double radius = ((pane.getPrefHeight()-2*inset)*Math.sqrt(3)/52)-0.01;
//        System.out.println(0.09*radius);

//        double xgap = pane.getPrefWidth()/27;
        xgap = radius;
        ygap = radius*Math.sqrt(3);

        xinit = (pane.getPrefWidth()/2) - (14*xgap);

        double xpos = xinit;
        double ypos=(2*radius/Math.sqrt(3))+ inset;

        hexagons = new Hexagon[19][15];
        defHexStyle = "-fx-fill: rgba(255,255,255,0.3);-fx-stroke: black;-fx-stroke-width: " + Math.ceil(0.09*radius);
        markedHexStyle = "-fx-fill: rgba(58,255,71,0.51);-fx-stroke: black;-fx-stroke-width: " + Math.ceil(0.09*radius);

        for(int i=0;i<s.length();i++){
            char sign = s.charAt(i);
            if(sign == ' ' || sign == '-') {
                xpos=xpos+xgap;
            }
            else if(sign == '\n') {
                xpos=xinit;
                ypos=ypos+ygap;

            }
            else {

                Hexagon hexagon = new Hexagon(xpos, ypos, radius);
                hexagons[convertY(ypos)][convertX(xpos)] = hexagon;
                hexagon.setStyle(defHexStyle);
                pane.getChildren().add(hexagon);


                if(sign != 'o') {

                    hexagon.setDisable(true);

                    BoardCircle circle = new BoardCircle();

                    circle.setRadius(0.75 * radius);
                    circle.setCenterX(xpos);
                    circle.setCenterY(ypos);

                    pane.getChildren().add(circle);

                    DropShadow drop = new DropShadow();
                    InnerShadow inner = new InnerShadow();
                    inner.setChoke(0.2);
                    drop.setInput(inner);
                    circle.setEffect(inner);

                    circle.setPlayerID(Integer.parseInt(sign + ""));
                    if(playerId == circle.getPlayerID()) {
                        circle.setPlayable(true);
                        pawnsList.add(circle);
                    }

                    circle.setStyle("-fx-fill: " + pawnsColors[circle.getPlayerID() - 1] + ";");

                    if (circle.isPlayable()) {

                        circle.setOnMouseClicked(mouseEvent -> {
                            if(!circle.equals(moveCircle)) {
                                if (moveCircle != null) {
                                    moveCircle.setStyle(moveStyle);
                                    resetHexagons();
                                }
                                moveCircle = circle;
                                moveStyle = circle.getStyle();
                                moveCircle.setStyle("-fx-fill: coral");
                                try {
                                    double y = circle.getCenterY();
                                    double x = circle.getCenterX();
                                    int convY = convertY(y);
                                    int convX = convertX(x);
                                    System.out.println("Y: " + convY + " X: " + convX);
                                    String answer = ClientThread.sendMessage("possible-moves;" + convY + ";" + convX);
                                    if(!answer.isEmpty()) {
                                        String[] pairs = answer.split(";");
                                        for (int j = 0; j < pairs.length; j++) {
                                            String[] pair = pairs[j].split(" ");
                                            int hexy = Integer.parseInt(pair[0]);
                                            int hexx = Integer.parseInt(pair[1]);
                                            Hexagon hex = hexagons[hexy][hexx];
                                            hex.setStyle(markedHexStyle);
                                            hex.setDisable(false);
                                        }
                                    }
                                } catch (ChineseCheckersException e){
                                    System.out.println("error during getting moves");
                                }
                            }
                        });
                    }
                }

                hexagon.setOnMouseClicked(mouseEvent -> {
                    double y = hexagon.getCenterY();
                    double x = hexagon.getCenterX();

                    System.out.println("Y: " + convertY(y) + " X: " + convertX(x));
                    if (moveCircle != null) {
                        double moveY = moveCircle.getCenterY();
                        double moveX = moveCircle.getCenterX();
                        try {
                            ClientThread.sendMessage("move;" + convertY(moveY) + ";" + convertX(moveX) + ";" + convertY(y) + ";" + convertX(x));
                            moveCircle.setStyle(moveStyle);
                            moveCircle.setCenterY(y);
                            moveCircle.setCenterX(x);
                            moveCircle.toFront();
                            moveCircle = null;

                            resetHexagons();

//                                FadeTransition ft = new FadeTransition(Duration.millis(150), moveCircle);
//                                ft.setFromValue(1.0);
//                                ft.setToValue(0.1);
//                                ft.play();
//                                ft.setOnFinished(actionEvent -> {
//                                    moveCircle.setStyle(moveStyle);
//                                    moveCircle.setCenterY(y);
//                                    moveCircle.setCenterX(x);
//                                    moveCircle.toFront();
//                                    resetHexagons();
//
//                                    FadeTransition ft2 = new FadeTransition(Duration.millis(150), moveCircle);
//                                    ft2.setFromValue(0.1);
//                                    ft2.setToValue(1.0);
//                                    ft2.play();
//
//                                    moveCircle = null;
//                                });

                        } catch (ChineseCheckersException e) {
                            e.printStackTrace();
                        }



                    }
                });

                xpos=xpos+xgap;


            }
        }
    }

    public int convertX(double x){
        return (int) Math.floor((x-xinit+10)/(2*xgap));
    }

    public int convertY(double y){
        return (int) Math.floor((y+10) / ygap);
    }
     private void resetHexagons(){
        for (Hexagon[] hexline : hexagons) {
            for (Hexagon hex : hexline) {
                if(hex != null) {
                    hex.setStyle(defHexStyle);
                    hex.setDisable(true);
                }
            }
        }
     }

    public void activatePawns() {
        for (BoardCircle circle : pawnsList) {
            circle.setDisable(false);
        }
    }

    public void disablePawns(){
        for (BoardCircle circle : pawnsList) {
            circle.setDisable(true);
        }
    }
}
