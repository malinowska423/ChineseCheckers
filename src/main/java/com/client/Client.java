package com.client;

public class Client {
    public static String nick;
    public static int playerId;
    public static void main(String[] args) {
        GUI.main(null);
    }

    public static void logIn(String nick) {
        Client.nick = nick;
        Client.playerId = Integer.parseInt(ClientThread.sendMessage(nick));
    }

    public static void logOut() {
        ClientThread.closeSocket();
    }
}
