package com.client;


import com.sun.javafx.scene.control.EmbeddedTextContextMenuContent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateRoom {

    private ToggleGroup gameModeList;
    private ToggleGroup numberOfPlayersList;
    private ToggleGroup numberOfAIPlayersList;

    @FXML
    HBox gameMode;
    @FXML
    HBox numberOfPlayers;
    @FXML
    HBox numberOfAIPlayers;



    @FXML
    private void createRoom(Event event){
        GUI.getInstance().gameRoomScene();
//        ((Button) event.getSource());
//        ((Stage) ((Button) event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void updateList() {
        System.out.println("working");


        String [] modes = new String[4];
        String [] players = {"2", "3", "4", "6"};
        String [] ai = {"2", "3", "4", "6"};
        modes[0] = "Basic";
        modes[1] = "Medium";
        modes[2] = "Hard";
        modes[3] = "Good";
        setListsData(modes, players, ai);

    }

    private void setListsData(String [] modes, String [] players, String [] ai){
        setListData(modes, gameMode, gameModeList, "mode_item");
        setListData(players, numberOfPlayers, numberOfPlayersList, "player_item");
        setListData(ai, numberOfAIPlayers, numberOfAIPlayersList, "player_item");
    }

    private void setListData(String [] data, HBox node, ToggleGroup list, String style) {
        list = new ToggleGroup();
        node.getChildren().removeAll(node.getChildren());
        ToggleButton mode;
        for (String datum : data) {
            mode = new ToggleButton(datum);
            list.getToggles().add(mode);
            node.getChildren().add(mode);
            mode.getStyleClass().add(style);
        }

    }

}
