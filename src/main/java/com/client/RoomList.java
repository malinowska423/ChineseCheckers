package com.client;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RoomList {

    @FXML
    VBox roomList;

    private ToggleGroup rooms = new ToggleGroup();

    @FXML
    private void newRoom(){
        GUI.getInstance().createRoomScene();
    }

    @FXML
    private void refresh(){


    }

    @FXML
    private void joinRoom(){
        if (rooms.getSelectedToggle() != null) {
            GUI.getInstance().gameRoomScene();
        }

    }
    //list format : "# # #" - id, players, capacity
    private void addToRoomList(String list) {
        String [] data = list.split(" ");
        String roomInfo = "Room #" + data[0] + "\t\t" + data[1] + "/" + data[2] + " players";
        ToggleButton room = new ToggleButton(Integer.parseInt(data[0]), roomInfo);
        room.setToggleGroup(rooms);
        roomList.getChildren().add(room);
    }

    class ToggleButton extends javafx.scene.control.ToggleButton {
        private int roomId;
        private String text;


        public ToggleButton(int roomId, String text) {
            super(text);
            this.roomId = roomId;
        }

        public int getRoomId() {
            return roomId;
        }


    }
}
