package com.client;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class BoardBuilder {

    public abstract void buildBoard (int playerId, String s, Pane pane, String [] colors,Room room);

    public static BoardBuilder runBuilder(String type) throws ChineseCheckersWindowException{
        switch (type) {
            case "Basic": {
                return new BasicBoardBuilder();
            }
            default: {
                throw new ChineseCheckersWindowException("Board type not implemented");
            }
        }
    }
}
