package com.client;

import javafx.application.Platform;

public class RoomThread implements Runnable {

    private Room room;
    private boolean isRunning;

    RoomThread(Room room)  {
        this.room = room;
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning) {
            System.out.println("waiting...");
            synchronized (this) {
                    String message;
                    try {
                        message = ClientThread.receiveMessage();
                        if (message.split(";")[0].equals("leave-room")){
                            break;
                        } else {
                            handleRequest(message);
                        }
                    } catch (ChineseCheckersException e) {
//                        Platform.runLater(() -> new ChineseCheckersWindowException(e.getMessage()).showWindow());
                        break;
                    }
            }
        }
    }

    boolean isRunning() {
        return isRunning;
    }

    //request pattern: "[code];message"
    //response pattern: "room-request;roomId;[code];message"
    private synchronized void handleRequest(String request) throws ChineseCheckersException{
        String requestCode = request.split(";")[0];
        System.out.println("\t" + requestCode);
        switch (requestCode) {
            case "your-turn": {
                isRunning = false;
                Platform.runLater(() -> room.takeTurn());
                break;
            }
            case "you-won": {
                room.setGameOn(false);
                break;
            }
            case "update-board": {
                //pattern: "update-board;x x x x"
                Platform.runLater(() -> room.updateBoard(request.split(";")[1]));
                break;
            }
            case "update-info": {
                Platform.runLater(() -> room.loadInfo(request.split(";")[1]));
                break;
            }
//            case "update-game": {
//                //pattern: "update-game;[true/false];[move-update];[message-update]"
//                Platform.runLater(() -> {
//                    room.updateBoard(request.split(";")[2]);
//                    room.loadInfo(request.split(";")[3]);
//                });
//                break;
//            }
            case "full-room": {
                //pattern: "full-room;message"
                room.setGameOn(true);
                Platform.runLater(() -> {
                    try {
                        room.loadRoom(request.split("<")[1]);
                        room.loadInfo(request.split("<")[2]);
                    } catch (ChineseCheckersException e) {
                        e.printStackTrace();
                    }
                });
                break;
            }
            case "update-room": {
                //pattern: "update-room;[message-update]|[roomInfo]"
                String roomInfo = request.substring(request.indexOf('|')+1);
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
            case "game-over": {
                room.setGameOn(false);
                isRunning = false;
                ClientThread.receiveMessage();
                Platform.runLater(() -> room.loadInfo("Game over, thanks!"));
                break;
            }
            default: {
                throw new ChineseCheckersException("Unhandled: " + request);
            }
        }
    }

    static void sendMessage(String message) {
        ClientThread.sendMessage(message);
    }
    static String sendRequest(String message) throws ChineseCheckersException{
        return ClientThread.sendRequest(message);
    }
}
