package com.client;

import javafx.scene.shape.Circle;

public class BoardCircle extends Circle {
    private boolean isPlayable;
    private boolean isField;
    private int playerID;

    public BoardCircle(){
        super();
        isPlayable = false;
        isField = false;
        this.playerID = -1;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setField(boolean field) {
        isField = field;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }

    public boolean isField() {
        return isField;
    }

    public boolean isPlayable() {
        return isPlayable;
    }
}
