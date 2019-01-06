package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RoomList {

    private ToggleGroup rooms = new ToggleGroup();

    @FXML
    VBox roomList;

    public void initialize() {
        refresh();
    }

    @FXML
    private void newRoom(){
        try {
            GUI.getInstance().launchCreateRoomScene(ClientThread.sendRequest("new-room-game-modes"));
        } catch (ChineseCheckersWindowException e) {
            e.showWindow();
        } catch (ChineseCheckersException e) {
            new ChineseCheckersWindowException(e.getMessage()).showWindow();
        }
    }

    @FXML
    public void refresh(){
        try {
            loadRoomList(getRoomList());
        } catch (ChineseCheckersException e) {
            new ChineseCheckersWindowException(e.getMessage()).showWindow();
        }
    }

    @FXML
    private void joinRoom(){
        if (rooms.getSelectedToggle() != null) {
            try {
                GUI.getInstance().launchGameRoomScene(ClientThread.sendRequest("join-room;" + getSelectedRoomId()));
            } catch (ChineseCheckersWindowException e) {
                e.showWindow();
            } catch (ChineseCheckersException e) {
                new ChineseCheckersWindowException(e.getMessage()).showWindow();
            }
        }

    }
    //list format : "# # #" - id, players, capacity
    private void addToRoomList(String list) {
        String [] data = list.split(" ");
        String roomInfo = "Room #" + data[0] + "\t\t" + data[1] + "/" + data[2] + " players";
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
        if (availableRooms[0].equals("")) {
            ToggleButton room = new ToggleButton("No rooms available");
            room.setDisable(true);
            room.getStyleClass().add("list-item");
            roomList.getChildren().add(room);
            return;
        }
        for (String datum :
                availableRooms) {
            addToRoomList(datum);
        }
    }

    private String [] getRoomList() throws ChineseCheckersException {
        //currentRooms pattern: "# # #;# # #;# # #:..."
        String currentRooms = ClientThread.sendRequest("current-room-list");
        return currentRooms.split(";");
    }

}
