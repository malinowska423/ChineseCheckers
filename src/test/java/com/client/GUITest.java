package com.client;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class GUITest {

    @Test
    void getInstance() {
    }

    @Test
    void main() throws IOException, ChineseCheckersWindowException {
//        GUI.main(null);
        assertThrows(IllegalStateException.class, () -> GUI.getInstance().launchGameRoomScene("1;1;1;1;1;11;1;6;6;5;65;556;;45;;5;5;45;5454;54;54;54;54;54;54;545;454;"));
        assertThrows(ChineseCheckersWindowException.class, () -> GUI.getInstance().launchCreateRoomScene("1;1;1;1;1;11;1"));
    }

    @Test
    void start() {
    }


    @Test
    void launchGameRoomScene() {
    }
}