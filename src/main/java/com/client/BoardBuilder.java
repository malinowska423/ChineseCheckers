package com.client;

import javafx.scene.layout.Pane;

public abstract class BoardBuilder {

    public abstract void buildBoard (int playerId, String s, Pane pane, String [] colors,Room room);
    public abstract void updateBoard (int y1, int x1, int y2, int x2);
    public abstract void activatePawns();
    public abstract void reset();

    static BoardBuilder runBuilder(String type) throws ChineseCheckersWindowException{
        switch (type) {
            case "Basic": {
                return new BasicBoardBuilder();
            }
            default: {
                throw new ChineseCheckersWindowException("Board type not implemented");
            }
        }
    }

    String getPossibleMoves(int y, int x, int roomId) throws ChineseCheckersException {
        return RoomThread.sendRequest("room-request;" + roomId + ";game-on;<possible-moves;" + y + ";" + x);
    }
}
