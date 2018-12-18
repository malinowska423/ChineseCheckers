package com.client;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class Room {
    @FXML
    GridPane boardGrid;
    public static void endGame(){

    }

    public void loadBoard(String type, int playerId, String board) {
        BoardBuilder boardBuilder;
        switch (type){
            case "basic":
            {
                boardBuilder = new BasicBoardBuilder();
            }break;
            default:
            {
                boardBuilder = new BoardBuilder() {
                    @Override
                    public GridPane buildBoard(int playerId, String s) {
                        return null;
                    }
                };
            } break;
        }
        boardGrid = boardBuilder.buildBoard(playerId, board);
    }
}
