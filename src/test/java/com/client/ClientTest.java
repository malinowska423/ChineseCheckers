package com.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void main() {
        Client.main(null);
    }

    @Test
    void connectToServer() {
        assertThrows(ChineseCheckersException.class, () -> Client.connectToServer());
    }

    @Test
    void logIn() {
        assertThrows(NullPointerException.class,() -> Client.logIn("nick"));
    }

    @Test
    void logOut() {
    }
}