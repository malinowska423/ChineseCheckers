package com.client;


import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {
    private double centerX;
    private double centerY;
    private BoardCircle circle;


    public Hexagon(double centerX, double centerY, double radius){
        super();
        this.centerX = centerX;
        this.centerY = centerY;
        double ytemp = radius/Math.sqrt(3);
        Double[] points = new Double[]{
                centerX, centerY+2*ytemp,
                centerX+radius, centerY+ytemp,
                centerX+radius, centerY-ytemp,
                centerX, centerY-2*ytemp,
                centerX-radius, centerY-ytemp,
                centerX-radius, centerY+ytemp,
        };
        this.getPoints().addAll(points);
        circle = null;
    }

    public BoardCircle getCircle() {
        return circle;
    }

    public void setCircle(BoardCircle circle) {
        this.circle = circle;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }
}
