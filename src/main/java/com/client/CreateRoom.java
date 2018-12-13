package com.client;


import com.sun.javafx.scene.control.EmbeddedTextContextMenuContent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CreateRoom {

    @FXML
    private void createRoom(Event event){
        GUI.getInstance().gameRoomScene();
//        ((Button) event.getSource());
//        ((Stage) ((Button) event.getSource())).getScene().getWindow().hide();
    }

}
