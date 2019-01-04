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
    Circle moveCircle = null;
    String moveStyle;
    ArrayList<BoardCircle> pawnsList = new ArrayList<>();


    @Override
    public void buildBoard(int playerId, String s, Pane pane) {

//        System.out.println(s);
        double inset = 10;

        pane.getChildren().add(new Circle(0,0,1));

        double radius = ((pane.getPrefHeight()-2*inset)*Math.sqrt(3)/52)-0.01;
        System.out.println(0.09*radius);

//        double xgap = pane.getPrefWidth()/27;
        double xgap = radius;
        double ygap = radius*Math.sqrt(3);

        double xinit = (pane.getPrefWidth()/2) - (14*xgap);

        double xpos = xinit;
        double ypos=(2*radius/Math.sqrt(3))+ inset;

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

                Hexagon hexagon = new Hexagon(xpos, ypos);
                double ytemp = radius/Math.sqrt(3);
                Double[] points = new Double[]{
                        xpos, ypos+2*ytemp,
                        xpos+xgap, ypos+ytemp,
                        xpos+xgap, ypos-ytemp,
                        xpos, ypos-2*ytemp,
                        xpos-xgap, ypos-ytemp,
                        xpos-xgap, ypos+ytemp,
                };
                hexagon.getPoints().addAll(points);
//                hexagon.setEffect(new DropShadow());

                hexagon.setStyle("-fx-fill: rgba(255,255,255,0.3);-fx-stroke: black;-fx-stroke-width: " + Math.ceil(0.09*radius));
                pane.getChildren().add(hexagon);

                BoardCircle circle = new BoardCircle();
                DropShadow drop = new DropShadow();
                InnerShadow inner = new InnerShadow();

                inner.setChoke(0.2);
                drop.setInput(inner);
                circle.setEffect(inner);

                circle.setRadius(0.75*radius);
                circle.setCenterX(xpos);
                circle.setCenterY(ypos);



                if(sign != 'o') {
                    pane.getChildren().add(circle);
                }

                xpos=xpos+xgap;

                switch (sign) {
//                    case 'o': {
//                        circle.setField(true);
//                        drop.setInput(null);
//                        circle.setStyle("-fx-fill: transparent;");
//
//                    }
//                    break;
                    case '1': {
                        if(playerId == 1) {
                            circle.setPlayable(true);
                            pawnsList.add(circle);
                        }
                        circle.setStyle("-fx-fill: red;");

                    }
                    break;
                    case '2': {
                        if(playerId == 2) {
                            circle.setPlayable(true);
                            pawnsList.add(circle);
                        }
                        circle.setStyle("-fx-fill: green;");

                    }
                    break;
                    case '3': {
                        if(playerId == 3) {
                            circle.setPlayable(true);
                            pawnsList.add(circle);
                        }
                        circle.setStyle("-fx-fill: blue;");

                    }
                    break;
                    case '4': {
                        if(playerId == 4) {
                            circle.setPlayable(true);
                            pawnsList.add(circle);
                        }
                        circle.setStyle("-fx-fill: purple;");

                    }
                    break;
                    case '5': {
                        if(playerId == 5) {
                            circle.setPlayable(true);
                            pawnsList.add(circle);
                        }
                        circle.setStyle("-fx-fill: brown;");

                    }
                    break;
                    case '6': {
                        if(playerId == 6) {
                            circle.setPlayable(true);
                            pawnsList.add(circle);
                        }
                        circle.setStyle("-fx-fill: yellow;");
                    }
                    break;
                }

                if(circle.isPlayable()) {



                    circle.setOnMouseClicked(mouseEvent -> {
                        double y = circle.getCenterY();
                        double x = circle.getCenterX();
                        System.out.println("Y: " + Math.floor((y+10) / ygap) + " X: " + Math.floor((x+10)/(2*xgap)));
                        if(moveCircle != null) {
                            moveCircle.setStyle(moveStyle);
                        }
                        moveCircle = circle;
                        moveStyle = circle.getStyle();
                        moveCircle.setStyle("-fx-fill: coral");
                    });
                }
                else {
                    circle.setOnMouseClicked(mouseEvent -> {
                        double y = circle.getCenterY();
                        double x = circle.getCenterX();
                        System.out.println(xgap);
                        System.out.println("y: " + y + " x: " + x);
                        System.out.println("Y: " + Math.floor((y+10) / ygap) + " X: " + Math.floor((x+10)/(2*xgap)));
                    });
                }

                hexagon.setOnMouseClicked(mouseEvent -> {
                    double y = hexagon.getCenterY();
                    double x = hexagon.getCenterX();
                    System.out.println(x);
                    System.out.println(2*xgap);
                    System.out.println("Y: " + Math.floor((y+10) / ygap) + " X: " + Math.floor((x+10)/(2*xgap)));
                    if (moveCircle != null) {
                        double moveY = moveCircle.getCenterY();
                        double moveX = moveCircle.getCenterX();
                        if( Math.abs(x - moveX) <= (2*xgap+10) && Math.abs(y - moveY) <= (ygap+10)) {
//                            moveCircle.setFill(null);
                            Label text = new Label("playing");
//                            try {
                                FadeTransition ft = new FadeTransition(Duration.millis(150), moveCircle);
                                ft.setFromValue(1.0);
                                ft.setToValue(0.1);
//                                ft.setCycleCount(Timeline.INDEFINITE);
//                                ft.setAutoReverse(false);
                                ft.play();
                                ft.setOnFinished(actionEvent -> {

                                    moveCircle.setStyle(moveStyle);
                                    moveCircle.setCenterY(y);
                                    moveCircle.setCenterX(x);
                                    moveCircle.toFront();

                                    FadeTransition ft2 = new FadeTransition(Duration.millis(150), moveCircle);
                                    ft2.setFromValue(0.1);
                                    ft2.setToValue(1.0);
                                    ft2.play();

                                    moveCircle = null;

                                });


//                                disablePawns();
                        }
                        else {
                            System.out.println("ZA DALEKO");
                        }
                    }
                });




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
