package com.client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BasicBoardBuilder extends BoardBuilder {
    Circle moveCircle = null;



    @Override
    public GridPane buildBoard(int playerId, String s) {
        System.out.println(s);
        GridPane grid = new GridPane();
        grid.setHgap(0);
        grid.setVgap(7);
        grid.setAlignment(Pos.CENTER);
        int radius = 12;
        int row = 0;
        int column = 0;
        for(int i=0;i<s.length();i++){
            char sign = s.charAt(i);
            if(sign == ' ' || sign == '-') {
                Label hspacing = new Label();
                hspacing.setMaxSize(5,5);
                hspacing.setMinSize(5,5);

//                hspacing.setStyle("-fx-min-height: " + radius + ";-fx-max-height: " + radius  + ";-fx-min-width: " + radius
//                        +";-fx-max-width: " + radius + ";");
//                grid.add(hspacing,column,row);
                column++;
//                System.out.println("SPACE");
            }
            else if(sign == '\n') {
//                System.out.println("JUMP");
//                row++;
//                Label vspacing = new Label();
//                vspacing.setStyle("-fx-min-height: 5;-fx-max-height: 10;-fx-min-width: 20;");
//                grid.add(vspacing,0,row);
                row++;
                column = 0;
            }
            else {
                BoardCircle circle = new BoardCircle();
                switch (sign) {
                    case 'o': {
//                        com.client.BoardCircle circle = new com.client.BoardCircle();
                        circle.setField(true);
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: black;");
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
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: red;");
                        grid.add(circle, column, row);
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
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: green;");
                        grid.add(circle, column, row);
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
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: yellow;");
                        grid.add(circle, column, row);
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
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: purple;");
                        grid.add(circle, column, row);
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
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: brown;");
                        grid.add(circle, column, row);
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
                        circle.setRadius(radius);
                        circle.setStyle("-fx-fill: blue;");
                        grid.add(circle, column, row);
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
                        moveCircle.setFill(Color.PLUM);
                    });
                }
                else if(circle.isField()) {
                    circle.setOnMouseClicked(mouseEvent -> {
                        int y = grid.getRowIndex(circle);
                        int x = grid.getColumnIndex(circle);
                        System.out.println("Y: " + y  + " X: " + x );
                        if (moveCircle != null) {
                            int moveY = grid.getRowIndex(moveCircle);
                            int moveX = grid.getColumnIndex(moveCircle);
                            if( Math.abs(x - moveX) <= 2 && Math.abs(y - moveY) <=2) {
                                moveCircle.setStyle(moveCircle.getStyle());
                                grid.getChildren().removeAll(circle, moveCircle);
                                grid.add(moveCircle, x, y);
                                grid.add(circle, moveX, moveY);
                                moveCircle = null;
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
                    });
                }


            }
        }
        return grid;
    }

}
