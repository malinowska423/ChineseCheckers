package com.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomThreadTest {

    @Test
    void mainTest() {
        assertDoesNotThrow(() -> new RoomThread(new Room()));
        RoomThread roomThread = new RoomThread(new Room());
        assertFalse(roomThread.isRunning());
        assertDoesNotThrow(() -> roomThread.run());
//        assertThrows(NullPointerException.class, () -> roomThread.run());
    }

    @Test
    void isRunning() {
    }

    @Test
    void sendMessage() {

    }

    @Test
    void sendRequest() {
    }
}