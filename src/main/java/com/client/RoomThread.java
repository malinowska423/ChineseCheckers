package com.client;

import javafx.application.Platform;

public class RoomThread implements Runnable {

    private Room room;
    private boolean myTurn;

    public RoomThread(Room room)  {
        this.room = room;
        myTurn = false;
    }

    @Override
    public void run() {
        while (room.isGameOn()) {
            System.out.println("waiting...");
            synchronized (this) {
                if (!myTurn) {

                    String message;
                    try {
                        message = ClientThread.receiveMessage();
                        handleRequest(message);
                    } catch (ChineseCheckersException e) {
                        Platform.runLater(() -> new ChineseCheckersWindowException(e.getMessage()).showWindow());
                    }
                } else {
                    room.takeTurn();
                    myTurn = false;
                }
            }
        }

    }


    //request pattern: "[code];message"
    //response pattern: "room-request;roomId;[code];message"
    public synchronized void handleRequest(String request) throws ChineseCheckersException{
        String requestCode = request.split(";")[0];
        switch (requestCode) {
            case "leave-room": {
                break;
            }
            case "update-game": {
                //pattern: "update-game;[true/false];[move-update];[message-update]"
                myTurn = Boolean.parseBoolean(request.split(";")[1]);
                Platform.runLater(() -> {
                    room.updateBoard(request.split(";")[2]);
                    room.loadInfo(request.split(":")[3]);
                });
                break;
            }
            case "full-room": {
                //pattern: "full-room;[true/false - myTurn];message"
                myTurn = Boolean.parseBoolean(request.split(";")[1]);
                Platform.runLater(() -> {
                    room.loadInfo(request.split(":")[2]);
                });
                break;
            }
            case "update-room": {
                //pattern: "update-room;[message-update]|[roomInfo]"
                String roomInfo = request.substring(request.indexOf('|')+1);
                System.out.println("\t" + roomInfo);
                Platform.runLater(() -> {
                    try {
                        room.loadRoom(roomInfo);
                        room.loadInfo(request.split(";")[1]);
                    } catch (ChineseCheckersException e) {
                        new ChineseCheckersWindowException(e.getMessage()).showWindow();
                    }
                });
                break;
            }
            default: {
                throw new ChineseCheckersException(request);
            }
        }
    }
}
