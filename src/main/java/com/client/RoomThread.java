package com.client;

public class RoomThread implements Runnable {

    private Room room;

    public RoomThread(Room room) {
        this.room = room;
    }

    @Override
    public void run() {

//            while (true) {
//                try {
//                    System.out.println("waiting...");
//                    room.loadRoom(ClientThread.receiveMessage());
//                } catch (ChineseCheckersException e) {
//                    new ChineseCheckersWindowException(e.getMessage()).showWindow();
//                }
//            }

    }
}
