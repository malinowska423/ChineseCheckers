package com.client;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

class ClientThreadTest {


    @Test
    void sendMessage() throws IOException {
        ServerSocket socket = new ServerSocket(7554);
        assertDoesNotThrow(ClientThread::new);
        assertDoesNotThrow(() -> ClientThread.sendMessage("mess"));
        socket.close();
        assertDoesNotThrow(() -> ClientThread.sendMessage("123"));
    }

}