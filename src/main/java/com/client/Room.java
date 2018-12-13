package com.client;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class Room {
    @FXML
    GridPane boardGrid;
    public static void endGame(){

    }

    public void loadBoard(int playerId, String board) {
        BoardBuilder boardBuilder = new BasicBoardBuilder();
        boardGrid = boardBuilder.buildBoard(playerId, board);
    }
}
