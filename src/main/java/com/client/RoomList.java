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
            //TODO: receive gameOptions from server and launch GUI with them
            GUI.getInstance().launchCreateRoomScene("1;4;3;Basic;2;3;4;6;1;2;3");
        } catch (ChineseCheckersWindowException e) {
            e.showWindow();
        }
    }

    @FXML
    public void refresh(){
        loadRoomList(getRoomList());
    }

    @FXML
    private void joinRoom(){
        if (rooms.getSelectedToggle() != null) {
            try {
                //TODO: send server id of selected room
                //getSelectedRoomId();
                //TODO: receive data from server about chosen room, save it to variable and launch game room
                String roomData = "";
                GUI.getInstance().launchGameRoomScene(roomData);
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

    private String getSelectedRoomId() {
        return rooms.getSelectedToggle().toString()
                .substring(rooms.getSelectedToggle().toString().indexOf('\'')+1, rooms.getSelectedToggle().toString().indexOf('\t'))
                .split("#")[1];
    }
    
    private void loadRoomList(String [] availableRooms) {
        roomList.getChildren().removeAll(roomList.getChildren());
        for (String datum :
                availableRooms) {
            addToRoomList(datum);
        }
    }

    private String [] getRoomList() {
        //TODO: receive room list from server and save it to variable currentRooms
        //currentRooms pattern: "# # #;# # #;# # #:..."
        String currentRooms = "";
        for (int i = 0; i < 10; i++) {
            int id = (int) (Math.random() * 100);
            currentRooms += id + " 3 5;";
        }
        return currentRooms.split(";");
    }

}
