package com.client;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class BoardBuilder {

    public abstract void buildBoard (int playerId, String s, Pane pane);
}
