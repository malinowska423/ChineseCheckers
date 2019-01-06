package com.client;

import javafx.application.Platform;

public class Client {

    public static void main(String[] args) {
        GUI.main(null);
    }

    static void connectToServer() throws ChineseCheckersException {
        Platform.runLater(new ClientThread());
    }

    static void logIn(String nick) {
        ClientThread.sendMessage("create-player;" + nick);
    }

    static void logOut() throws ChineseCheckersException {
        ClientThread.closeSocket();
    }
}
