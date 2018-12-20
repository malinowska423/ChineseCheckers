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


    public static void endGame(){

    }

    @FXML
    private void play(){
        playButton.setDisable(true);
        loadTitle("Room#342");
        loadInfo("Player#112 ended his move. Your turn!");
        String [] players = {"Player#336", "Player#88", "Player#112"};
        String [] colors = {"red", "green", "blue"};
        loadPlayers(players, colors);

        String in =
                "- - - - - - - 1 - - - - - - - \n" +
                " - - - - - - 1 1 - - - - - - - \n" +
                "- - - - - - 1 1 1 - - - - - - \n" +
                " - - - - - 1 1 1 1 - - - - - - \n" +
                "- o o o o o o o o o 2 2 2 2 - \n" +
                " - o o o o o o o o o 2 2 2 - - \n" +
                "- - o o o o o o o o o 2 2 - - \n" +
                " - - o o o o o o o o o 2 - - - \n" +
                "- - - o o o o o o o o o - - - \n" +
                " - - o o o o o o o o o 3 - - - \n" +
                "- - o o o o o o o o o 3 3 - - \n" +
                " - o o o o o o o o o 3 3 3 - - \n" +
                "- o o o o o o o o o 3 3 3 3 - \n" +
                " - - - - - o o o o - - - - - - \n" +
                "- - - - - - o o o - - - - - - \n" +
                " - - - - - - o o - - - - - - - \n" +
                "- - - - - - - o - - - - - - - \n";
        loadBoard("basic", 2, in);
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
}
