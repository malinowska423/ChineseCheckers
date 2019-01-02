package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RoomList {

    @FXML
    VBox roomList;

    private ToggleGroup rooms = new ToggleGroup();

    @FXML
    private void newRoom(){
        try {
            GUI.getInstance().launchCreateRoomScene("1;4;3;Basic;2;3;4;6;1;2;3");
        } catch (ChineseCheckersWindowException e) {
            e.showWindow();
        }
    }

    @FXML
    private void refresh(){

        String [] currentRooms = new String[10];
        for (int i = 0; i < 10; i++) {
            int id = (int) (Math.random() * 100);
            currentRooms[i] = id + " 3 5";
        }
        loadRoomList(currentRooms);
    }

    @FXML
    private void joinRoom(){
        if (rooms.getSelectedToggle() != null) {
            try {
                GUI.getInstance().launchGameRoomScene(getToggleTitle(rooms.getSelectedToggle()));
            } catch (ChineseCheckersWindowException e) {
                e.showWindow();
            }
        }

    }
    //list format : "# # #" - id, players, capacity
    private void addToRoomList(String list) {
        String [] data = list.split(" ");
        String roomInfo = "Room#" + data[0] + "\t\t" + data[1] + "/" + data[2] + " players";
        ToggleButton room = new ToggleButton(roomInfo);
        room.getStyleClass().add("list-item");
        room.setToggleGroup(rooms);
        roomList.getChildren().add(room);
    }

    private String getToggleTitle(Toggle toggle) {
        return toggle.toString().substring(rooms.getSelectedToggle().toString().indexOf('\'')+1, rooms.getSelectedToggle().toString().indexOf('\t'));
    }
    
    private void loadRoomList(String [] availableRooms) {
        roomList.getChildren().removeAll(roomList.getChildren());
        for (String datum :
                availableRooms) {
            addToRoomList(datum);
        }
    }

}
