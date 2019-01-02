package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Room {
    @FXML
    Pane pane;
    @FXML
    Label title;
    @FXML
    Label infoDisplay;
    @FXML
    VBox playersList;
    @FXML
    Button playButton;

    public void initialize(String data) throws ChineseCheckersWindowException{
        try {
            loadRoom(data);
        } catch (ChineseCheckersException e) {
            throw new ChineseCheckersWindowException(e.getMessage());
        }
    }


    public static void endGame(){

    }

    @FXML
    private void play(){
        playButton.setDisable(true);
    }

    public void loadBoard(String type, int playerId, String board) {
        BoardBuilder boardBuilder;
        switch (type){
            case "Basic":
            {
                boardBuilder = new BasicBoardBuilder();
            }break;
            default:
            {
                boardBuilder = new BoardBuilder() {
                    @Override
                    public void buildBoard(int playerId, String s, Pane pane) {
                        pane.getChildren().removeAll(pane.getChildren());
                    }
                };
            } break;
        }

        boardBuilder.buildBoard(playerId, board, pane);
    }

    private void loadTitle(String name) {
        title.setText(name);
    }

    private void loadInfo(String info){
        infoDisplay.setText(info);
    }

    private void loadPlayers(String [] players, String [] colors) {
        playersList.getChildren().removeAll(playersList.getChildren());
        for (String player:
             players) {
            Label item = new Label(player);
            playersList.getChildren().add(item);
            if (playersList.getChildren().indexOf(item)%2 == 0){
                item.getStyleClass().add("bg_color");
            }
        }
        for(int i = 0; i < colors.length ; i++) {
            playersList.getChildren().get(i).setStyle("-fx-text-fill: " + colors[i] + ";");
        }
        playersList.getChildren().get(0).getStyleClass().add("first");
    }

    private void loadRoom(String roomData) throws ChineseCheckersException {
        if (roomData != null && !roomData.isEmpty()){
            //roomData pattern: "title;message;numberOfPlayers;[players];[colors];gameMode;playerId;board"
            String [] data = roomData.split(";");
            loadTitle(data[0]);
            loadInfo(data[1]);
            int numberOfPlayers = Integer.parseInt(data[2]);
            String [] players = new String [numberOfPlayers];
            String [] colors = new String[numberOfPlayers];
            int playerIndex = 3;
            int colorIndex = 3 + numberOfPlayers;
            for (int i = 0; i < numberOfPlayers; i++, playerIndex++, colorIndex++) {
                players[i] = data[playerIndex];
                colors[i] = data[colorIndex];
            }
            loadPlayers(players, colors);
            String gameMode = data[3 + 2*numberOfPlayers];
            int playerId = Integer.parseInt(data[4 + 2*numberOfPlayers]);
            String board = data[5 + 2*numberOfPlayers];
            loadBoard(gameMode,playerId,board);
        } else {
            throw new ChineseCheckersException("Empty room data");
        }
    }
}
