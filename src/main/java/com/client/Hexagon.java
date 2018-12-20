package com.client;


import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {
    double centerX;
    double centerY;

    public Hexagon() {
        super();
    }

    public Hexagon(double centerX, double centerY){
        super();
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
