package com.client;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class BasicBoardBuilder extends BoardBuilder {
    private BoardCircle moveCircle = null;
    private String moveStyle;
    private ArrayList<BoardCircle> pawnsList = new ArrayList<>();
    private Hexagon[][] hexagons;


    @Override
    public void buildBoard(int playerId, String s, Pane pane, String [] pawnsColors) {

//        System.out.println(s);
        double inset = 10;

        double radius = ((pane.getPrefHeight()-2*inset)*Math.sqrt(3)/52)-0.01;
//        System.out.println(0.09*radius);

//        double xgap = pane.getPrefWidth()/27;
        double xgap = radius;
        double ygap = radius*Math.sqrt(3);

        double xinit = (pane.getPrefWidth()/2) - (14*xgap);

        double xpos = xinit;
        double ypos=(2*radius/Math.sqrt(3))+ inset;

        hexagons = new Hexagon[19][15];

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
                hexagons[convertY(ypos,ygap)][convertX(xpos, xinit, xgap)] = hexagon;
                hexagon.setStyle("-fx-fill: rgba(255,255,255,0.3);-fx-stroke: black;-fx-stroke-width: " + Math.ceil(0.09*radius));
                pane.getChildren().add(hexagon);


                if(sign != 'o') {

                    hexagon.setOccupied(true);

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
                            double y = circle.getCenterY();
                            double x = circle.getCenterX();
                            System.out.println("Y: " + convertY(y, ygap) + " X: " + convertX(x, xinit, xgap));
                            if (moveCircle != null) {
                                moveCircle.setStyle(moveStyle);
                            }
                            moveCircle = circle;
                            moveStyle = circle.getStyle();
                            moveCircle.setStyle("-fx-fill: coral");
                        });
                    }
                }


                hexagon.setOnMouseClicked(mouseEvent -> {
                    double y = hexagon.getCenterY();
                    double x = hexagon.getCenterX();

                    System.out.println("Y: " + convertY(y,ygap) + " X: " + convertX(x,xinit,xgap));
                    if (moveCircle != null && !hexagon.isOccupied()) {
                        double moveY = moveCircle.getCenterY();
                        double moveX = moveCircle.getCenterX();
//                        if( Math.abs(x - moveX) <= (2*xgap+10) && Math.abs(y - moveY) <= (ygap+10)) {

//                                FadeTransition ft = new FadeTransition(Duration.millis(150), moveCircle);
//                                ft.setFromValue(1.0);
//                                ft.setToValue(0.1);
////                                ft.setCycleCount(Timeline.INDEFINITE);
////                                ft.setAutoReverse(false);
//                                ft.play();
//                                ft.setOnFinished(actionEvent -> {
//
//                                    moveCircle.setStyle(moveStyle);
//                                    moveCircle.setCenterY(y);
//                                    moveCircle.setCenterX(x);
//                                    moveCircle.toFront();
//
//                                    FadeTransition ft2 = new FadeTransition(Duration.millis(150), moveCircle);
//                                    ft2.setFromValue(0.1);
//                                    ft2.setToValue(1.0);
//                                    ft2.play();
//
//                                    moveCircle = null;
//                                });

                            hexagon.setOccupied(true);
                            hexagons[convertY(moveY,ygap)][convertX(moveX, xinit, xgap)].setOccupied(false);
                            moveCircle.setStyle(moveStyle);
                            moveCircle.setCenterY(y);
                            moveCircle.setCenterX(x);
                            moveCircle.toFront();
                            moveCircle = null;


//                                disablePawns();
//                        }
//                        else {
//                            System.out.println("2far");
//                        }
                    }
                });

                xpos=xpos+xgap;


            }
        }
    }

    public int convertX(double x, double xinit, double xgap){
        return (int) Math.floor((x-xinit+10)/(2*xgap));
    }

    public int convertY(double y, double ygap){
        return (int) Math.floor((y+10) / ygap);
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
