package com.client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class BasicBoardBuilder extends BoardBuilder {
    Circle moveCircle = null;
    ArrayList<BoardCircle> pawnsList = new ArrayList<>();
    int currentPlayer;
    Label title;


    public void setTitle(Label title) {
        this.title = title;
    }

    @Override
    public void buildBoard(int playerId, String s, GridPane grid) {
        currentPlayer = playerId;
        System.out.println(s);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: rgba(255,255,255,.25)");
        int radius = 12;
        int row = 0;
        int column = 0;
        for(int i=0;i<s.length();i++){
            char sign = s.charAt(i);
            if(sign == ' ' || sign == '-') {
                Label hspacing = new Label();
                hspacing.setStyle("-fx-min-height: " + 2*radius  + ";-fx-min-width: " + 2*radius + ";");
                grid.add(hspacing,column,row);
                column++;
//                System.out.println("SPACE");
            }
            else if(sign == '\n') {
//                System.out.println("JUMP");
                row++;
                Label vspacing = new Label();
                vspacing.setStyle("-fx-min-height: 5;-fx-max-height: 10;-fx-min-width: 20;");
                grid.add(vspacing,0,row);
                row++;
                column = 0;
            }
            else {
                BoardCircle circle = new BoardCircle();
                DropShadow drop = new DropShadow();
//                drop.setRadius(50);
                drop.setHeight(17);
                drop.setWidth(25);
                drop.setSpread(0.9);
//                drop.setColor(Color.GRAY);
//                drop.setColor(Color.GRAY);
//                drop.setBlurType(BlurType.GAUSSIAN);
                circle.setEffect(drop);


                switch (sign) {
                    case 'o': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
                        circle.setField(true);
                        circle.setRadius(radius);
//                        circle.setStyle("-fx-fill: gray;");
                        grid.add(circle, column, row);
                        column++;
//                        System.out.println("FOUND o");
                    }
                    break;
                    case '1': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
//                        circle.setPlayerId(1);
                        if(playerId == 1) {
                            circle.setPlayable(true);
                        }
                        circle.setPlayerID(1);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: red;");
                        grid.add(circle, column, row);
                        pawnsList.add(circle);
                        column++;
//                        System.out.println("FOUND 1");
                    }
                    break;
                    case '2': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
//                        circle.setPlayerId(2);
                        if(playerId == 2) {
                            circle.setPlayable(true);
                        }
                        circle.setPlayerID(2);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: green;");
                        grid.add(circle, column, row);
                        pawnsList.add(circle);
                        column++;
//                        System.out.println("FOUND 2");
                    }
                    break;
                    case '3': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
//                        circle.setPlayerId(3);
                        if(playerId == 3) {
                            circle.setPlayable(true);
                        }
                        circle.setPlayerID(3);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: blue;");
                        grid.add(circle, column, row);
                        pawnsList.add(circle);
                        column++;
//                        System.out.println("FOUND 3");
                    }
                    break;
                    case '4': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
//                        circle.setPlayerId(4);
                        if(playerId == 4) {
                            circle.setPlayable(true);
                        }
                        circle.setPlayerID(4);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: purple;");
                        grid.add(circle, column, row);
                        pawnsList.add(circle);
                        column++;
//                        System.out.println("FOUND 4");
                    }
                    break;
                    case '5': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
//                        circle.setPlayerId(5);
                        if(playerId == 5) {
                            circle.setPlayable(true);
                        }
                        circle.setPlayerID(5);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: brown;");
                        grid.add(circle, column, row);
                        pawnsList.add(circle);
                        column++;
//                        System.out.println("FOUND 5");
                    }
                    break;
                    case '6': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
//                        circle.setPlayerId(6);
                        if(playerId == 6) {
                            circle.setPlayable(true);
                        }
                        circle.setPlayerID(6);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: yellow;");
                        grid.add(circle, column, row);
                        pawnsList.add(circle);
                        column++;
//                        System.out.println("FOUND 6");
                    }
                    break;
                }

                if(circle.isPlayable()) {
                    circle.setOnMouseClicked(mouseEvent -> {
                        int y = grid.getRowIndex(circle);
                        int x = grid.getColumnIndex(circle);
                        System.out.println("Y: " + y / 2 + " X: " + x / 2);
                        if(moveCircle != null) {
                            moveCircle.setFill(null);
                            String style = moveCircle.getStyle();
                            moveCircle.setStyle("");
                            moveCircle.setStyle(style);
                        }
                        moveCircle = circle;
                        moveCircle.setFill(Color.CORAL);
                    });
                }
                else if(circle.isField()) {
                    circle.setOnMouseClicked(mouseEvent -> {
                        int y = grid.getRowIndex(circle);
                        int x = grid.getColumnIndex(circle);
                        System.out.println("Y: " + y / 2 + " X: " + x / 2);
                        if (moveCircle != null) {
                            int moveY = grid.getRowIndex(moveCircle);
                            int moveX = grid.getColumnIndex(moveCircle);
                            if( Math.abs(x - moveX) <= 2 && Math.abs(y - moveY) <=2) {
                                moveCircle.setStyle(moveCircle.getStyle());
                                grid.getChildren().removeAll(circle, moveCircle);
                                grid.add(moveCircle, x, y);
                                grid.add(circle, moveX, moveY);
                                moveCircle = null;
                                disableAllPawns();
                                currentPlayer = (currentPlayer%3) + 1;
                                title.setText("Player " + currentPlayer + " turn!");
                                System.out.println("Player " + currentPlayer + " Turn");
                                activatePawns(currentPlayer);

                            }
                            else {
                                System.out.println("ZA DALEKO");
                            }
                        }
                    });
                }
                else {
                    circle.setOnMouseClicked(mouseEvent -> {
                        int y = grid.getRowIndex(circle);
                        int x = grid.getColumnIndex(circle);
                        System.out.println("Y: " + y / 2 + " X: " + x / 2);
                        if(moveCircle != null) {
                            moveCircle.setFill(null);
                            String style = moveCircle.getStyle();
                            moveCircle.setStyle("");
                            moveCircle.setStyle(style);
                        }
                        moveCircle = circle;
                        moveCircle.setFill(Color.CORAL);
                    });
                }


            }
        }
        disableAllPawns();
        activatePawns(playerId);
    }

    public void activatePawns(int playerId) {
        for (BoardCircle circle : pawnsList) {
            if(circle.getPlayerID() == playerId) {
                circle.setDisable(false);
            }
        }
    }

    public void disableAllPawns(){
        for (BoardCircle circle : pawnsList) {
            circle.setDisable(true);
        }
    }
}
