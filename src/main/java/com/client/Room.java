package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Room {
    private int roomId;
    private boolean gameOn;
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

    RoomThread roomThread;

    public int getRoomId() {
        return roomId;
    }

    public void initialize(String data) throws ChineseCheckersWindowException{
        try {
            loadRoom(data);
            roomId = Integer.parseInt(data.split(";")[0]);
            gameOn = false;
        } catch (ChineseCheckersException e) {
            throw new ChineseCheckersWindowException(e.getMessage());
        }
    }

    public void leaveRoom() throws ChineseCheckersException{
        if (!isGameOn()) {
            ClientThread.sendMessage("room-request;" + roomId + ";leave-room");
        } else {
            throw new ChineseCheckersException("Finish the game first");
        }
    }

    @FXML
    private void play(){
        try {
            ClientThread.sendMessage("room-request;"+ roomId +";start-game");
            playButton.setDisable(true);
            gameOn = true;
            roomThread = new RoomThread(this);
            Thread thread = new Thread(roomThread);
            thread.start();
        } catch (ChineseCheckersException e) {
            new ChineseCheckersWindowException(e.getMessage()).showWindow();
        }
    }

    private void loadBoard(String type, int playerId, String board, String [] colors) {
        BoardBuilder boardBuilder = null;
        try {
            boardBuilder = BoardBuilder.runBuilder(type);
        } catch (ChineseCheckersWindowException e) {
            e.showWindow();
        }
        boardBuilder.buildBoard(playerId, board, pane, colors);
    }

    public void updateBoard(String coordinates) {
        //TODO: call method in Board
    }

    private void loadTitle(String name) {
        title.setText(name);
    }

    public void loadInfo(String info){
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

    public void loadRoom(String roomData) throws ChineseCheckersException {
        System.out.println(roomData);
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

    public boolean isGameOn() {
        return gameOn;
    }

    public void takeTurn() {
        playButton.setText("Send your move");
        playButton.setDisable(false);
        playButton.setOnMouseClicked(mouseEvent -> {
            String moves = "";
            try {
                ClientThread.sendMessage("room-request;" + roomId + ";game-on<move;" + moves);
                playButton.setDisable(true);
            } catch (ChineseCheckersException e) {
                new ChineseCheckersWindowException(e.getMessage()).showWindow();
            }
        });
    }
}
