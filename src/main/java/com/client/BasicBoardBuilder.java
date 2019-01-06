package com.client;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class BasicBoardBuilder extends BoardBuilder {
    private BoardCircle moveCircle;
    private String moveCircleStyle;
    private final ArrayList<BoardCircle> pawnsList = new ArrayList<>();
    private final Hexagon[][] hexagons = new Hexagon[19][15];;

    private String defHexStyle;
    private String markedHexStyle;

    private double xinit;
    private double xgap;
    private double ygap;

    public BasicBoardBuilder(){
        moveCircle = null;
        moveCircleStyle = null;
    }


    @Override
    public void buildBoard(int playerId, String s, Pane pane, String[] pawnsColors,Room room) {

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
                                    moveCircle.setStyle(moveCircleStyle);
                                    resetHexagons();
                                }
                                moveCircle = circle;
                                moveCircleStyle = circle.getStyle();
                                moveCircle.setStyle("-fx-fill: coral");
                                try {
                                    double y = circle.getCenterY();
                                    double x = circle.getCenterX();
                                    int convY = convertY(y);
                                    int convX = convertX(x);
                                    System.out.println("Y: " + convY + " X: " + convX);
                                    String answer = ClientThread.sendMessage("room-request;" + room.getRoomId() + ";game-on;<possible-moves;" + convY + ";" + convX);
                                    if(!answer.isEmpty()) {
                                        String[] pairs = answer.split("<")[1].split(";");
                                        for (String pair1 : pairs) {
                                            String[] pair = pair1.split(" ");
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

                        try {
                        double moveY = moveCircle.getCenterY();
                        double moveX = moveCircle.getCenterX();

                        String moves = convertY(moveY) + ";" + convertX(moveX) + ";" + convertY(y) + ";" + convertX(x);
                        room.setMoves(moves);

                        ClientThread.sendMessage("room-request;" + room.getRoomId() + ";game-on;<move;" + moves);

                        moveCircle.setStyle(moveCircleStyle);
                        moveCircle.setCenterY(y);
                        moveCircle.setCenterX(x);
                        moveCircle.toFront();
                        moveCircle = null;

                        resetHexagons();
                        } catch (ChineseCheckersException e) {
                            e.printStackTrace();
                        }

//                        disablePawns();

//                            FadeTransition ft = new FadeTransition(Duration.millis(150), moveCircle);
//                            ft.setFromValue(1.0);
//                            ft.setToValue(0.1);
//                            ft.play();
//                            ft.setOnFinished(actionEvent -> {
//                                moveCircle.setStyle(moveCircleStyle);
//                                moveCircle.setCenterY(y);
//                                moveCircle.setCenterX(x);
//                                moveCircle.toFront();
//                                resetHexagons();
//
//                                FadeTransition ft2 = new FadeTransition(Duration.millis(150), moveCircle);
//                                ft2.setFromValue(0.1);
//                                ft2.setToValue(1.0);
//                                ft2.play();
//
//                                moveCircle = null;
//                            });




                    }
                });

                xpos=xpos+xgap;


            }
        }
    }

    private int convertX(double x){
        return (int) Math.floor((x-xinit+10)/(2*xgap));
    }

    private int convertY(double y){
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
