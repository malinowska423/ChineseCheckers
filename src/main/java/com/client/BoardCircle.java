package com.client;

import javafx.scene.shape.Circle;

public class BoardCircle extends Circle {
    private boolean isPlayable;
    private int playerID;

    public BoardCircle(){
        super();
        isPlayable = false;
        this.playerID = -1;
    }


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }


    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }


    public boolean isPlayable() {
        return isPlayable;
    }
}
