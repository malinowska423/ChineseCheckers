package com.client;

import javafx.scene.layout.GridPane;

public abstract class BoardBuilder {

    public abstract GridPane buildBoard (int PlayerId, String s);
}