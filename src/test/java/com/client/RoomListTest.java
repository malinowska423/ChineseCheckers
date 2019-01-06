package com.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomListTest {

    @Test
    void initialize() {
        assertThrows(IllegalStateException.class, () ->new RoomList().initialize());
    }
}