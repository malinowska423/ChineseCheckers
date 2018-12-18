package com.client;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class CreateRoom {

    private ToggleGroup gameModeList = new ToggleGroup();
    private ToggleGroup numberOfPlayersList = new ToggleGroup();
    private ToggleGroup numberOfAIPlayersList = new ToggleGroup();

    @FXML
    HBox gameMode;
    @FXML
    HBox numberOfPlayers;
    @FXML
    HBox numberOfAIPlayers;

    @FXML
    private void createRoom(){
        //TODO: call method sending data to server and receive roomId before opening new window
        GUI.getInstance().gameRoomScene("New game");
    }

    @FXML
    private void updateList() {
        //TODO: create method downloading game options data from server
        String[] modes = {"Basic Pro", "Easy", "Hard", "Good"};
        String [] players = {"2", "3", "4", "6"};
        String [] ai = {"1", "2", "3", "4", "5"};
        setListsData(modes, players, ai);

    }

    private void setListsData(String [] modes, String [] players, String [] ai){
        setListData(modes, gameMode, gameModeList, "mode_item");
        setListData(players, numberOfPlayers, numberOfPlayersList, "player_item");
        setListData(ai, numberOfAIPlayers, numberOfAIPlayersList, "player_item");
    }

    private void setListData(String [] data, HBox node, ToggleGroup list, String style) {
        list.getToggles().removeAll(list.getToggles());
        node.getChildren().removeAll(node.getChildren());
        ToggleButton mode;
        for (String datum : data) {
            mode = new ToggleButton(datum);
            list.getToggles().add(mode);
            node.getChildren().add(mode);
            mode.getStyleClass().add(style);
        }

    }

    private String getNewRoomData(){
        return (((ToggleButton) gameModeList.getSelectedToggle()).getText() + ";") + (((ToggleButton) numberOfPlayersList.getSelectedToggle()).getText() + ";") + (((ToggleButton) numberOfAIPlayersList.getSelectedToggle()).getText() + ";");
    }

}
