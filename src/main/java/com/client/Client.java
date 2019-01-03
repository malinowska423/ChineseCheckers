package com.client;

public class Client {
    public static String nick;
    public static int playerId;
    public static void main(String[] args) {
        GUI.main(null);
    }

    public static void logIn(String nick) throws ChineseCheckersException {
        Client.nick = nick;
        Client.playerId = Integer.parseInt(ClientThread.sendMessage("create-player;" + nick));
    }

    public static void logOut() throws ChineseCheckersException {
        ClientThread.closeSocket();
    }
}
