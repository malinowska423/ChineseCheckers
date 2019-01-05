package com.client;

import java.util.ArrayList;

public class Client {
    public static String nick;
    public static int playerId;
    private static ArrayList<Room> openedRooms = new ArrayList<>();
    public static void main(String[] args) {
        GUI.main(null);
    }

    public static void logIn(String nick) throws ChineseCheckersException {
        Client.nick = nick;
        Client.playerId = Integer.parseInt(ClientThread.sendMessage("create-player;" + nick));
    }

    public static void logOut() throws ChineseCheckersException {
        if (canLeave()) {
            ClientThread.closeSocket();
        } else {
            throw new ChineseCheckersException("Leave all the rooms first");
        }
    }

    public static void enterRoom(Room room) {
        openedRooms.add(room);
    }

    public static void leaveRoom(Room room) {
        openedRooms.remove(room);
    }

    public static boolean canLeave() {
        return openedRooms.isEmpty();
    }
}
