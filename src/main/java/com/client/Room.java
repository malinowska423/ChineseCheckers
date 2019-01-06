package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Room {
    private int roomId;
    private boolean gameOn;
    private RoomThread roomThread;
    private String moves;
    private BoardBuilder builder;
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


    int getRoomId() {
        return roomId;
    }

    void initialize(String data) throws ChineseCheckersWindowException{
        try {
            loadRoom(data);
            roomId = Integer.parseInt(data.split(";")[0]);
            gameOn = false;
            moves = "";
        } catch (ChineseCheckersException e) {
            throw new ChineseCheckersWindowException(e.getMessage());
        }
    }

    void leaveRoom() throws ChineseCheckersException{
        if (isGameOn() || (roomThread != null && roomThread.isRunning())) {
            throw new ChineseCheckersException("You cannot leave during the game");
        } else {
            RoomThread.sendRequest("room-request;" + roomId + ";leave-room");
        }
    }

    @FXML
    private void play(){
        RoomThread.sendMessage("room-request;"+ roomId +";start-game");
        playButton.setDisable(true);
        roomThread = new RoomThread(this);
        new Thread(roomThread).start();
    }

    private void loadBoard(String type, int playerId, String board, String [] colors) {
        if(gameOn) {
            try {
                builder = BoardBuilder.runBuilder(type);
            } catch (ChineseCheckersWindowException e) {
                e.showWindow();
            }
            builder.buildBoard(playerId, board, pane, colors, this);
        }
    }

    void updateBoard(String coordinates) {
        if (!coordinates.equals("pass")) {
            String[] data = coordinates.split(" ");
            int[] parsedData = new int[4];
            for (int i = 0; i < 4; i++) {
                parsedData[i] = Integer.parseInt(data[i]);
            }
            builder.updateBoard(parsedData[0], parsedData[1], parsedData[2], parsedData[3]);
        }
    }

    private void loadTitle(String name) {
        title.setText(name);
    }

    void loadInfo(String info){
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

        for(int i = 0; i < players.length ; i++) {
            playersList.getChildren().get(i).setStyle("-fx-text-fill: " + colors[i] + ";");
        }
        playersList.getChildren().get(0).getStyleClass().add("first");
    }

    void loadRoom(String roomData) throws ChineseCheckersException {
        if (roomData != null && !roomData.isEmpty()){
            //roomData pattern: "roomId;message;numberOfPlayers;[players];[colors];gameMode;playerId;board"
            String [] data = roomData.split(";");
            loadTitle("Room #" + data[0]);
            loadInfo(data[1]);
            int numberOfPlayers = Integer.parseInt(data[2]);
            String [] players = new String [numberOfPlayers];
            String [] colors = new String[6];
            int index = 3;
            for (int i = 0; i < numberOfPlayers; i++, index++) {
                players[i] = data[index];
            }
            for (int i = 0; i < 6; i++, index++) {
                colors[i] = data[index];
            }
            String gameMode = data[9 + numberOfPlayers];
            int playerId = Integer.parseInt(data[10 + numberOfPlayers]);
            String board = data[11 + numberOfPlayers];
            board = board.replace("\\n", "\n");
            loadPlayers(players, currentColors(board, colors));
            loadBoard(gameMode,playerId,board, colors);
        } else {
            throw new ChineseCheckersException("Empty room data");
        }
    }

    private String [] currentColors(String board, String [] colors) {
        StringBuilder currentColors = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            if (board.contains(i + "")) {
                currentColors.append(colors[i - 1]).append(";");
            }
        }
        return currentColors.toString().split(";");
    }

    private boolean isGameOn() {
        return gameOn;
    }

    void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    void takeTurn() {
        builder.activatePawns();
        playButton.setText("Send your move");
        playButton.setDisable(false);
        playButton.setOnAction(actionEvent -> {
            if (moves.isEmpty()){
                moves = "pass";
            }
            RoomThread.sendMessage("room-request;" + roomId + ";game-on;<move;" + moves);
            RoomThread.sendMessage("room-request;" + roomId + ";end-turn");
            playButton.setDisable(true);
            moves = "";
            new Thread(roomThread).start();
        });
    }

    void setMoves(String moves) {
        this.moves = moves;
    }
}
