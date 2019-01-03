package com.client;

public class Client {
    public static String nick;
    public static int playerId;
    public static void main(String[] args) {
        GUI.main(null);
    }

    public static void logIn(String nick) {
        //TODO: send nick to server, create player and receive playerId
        Client.nick = nick;
//        Client.playerId = random();
    }

    public static void logOut() {
        //TODO: send info to server that player has shut down the app
    }
}
