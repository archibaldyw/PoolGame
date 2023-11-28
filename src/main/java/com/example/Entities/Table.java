package com.example.Entities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Table {
    private double friction;
    ArrayList<Circle> pockets;
    Canvas canvas;

    double pocketSize;

    public Table() {
        canvas = new Canvas();
        pockets = new ArrayList<>();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public ArrayList<Circle> getPockets() {
        return pockets;
    }

    public Vector2D getSize() {
        return new Vector2D(canvas.getWidth(), canvas.getHeight());
    }

    public Paint getColor() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Paint color = gc.getFill();
        return color;
    }

    public double getFriction() {
        return friction;
    }

    public void setSize(Vector2D size) {
        canvas.setWidth(size.x);
        canvas.setHeight(size.y);
    }

    public void setColor(Vector3D color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(color.toColor());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public void setPockets(ArrayList<Circle> holes) {
        this.pockets = pockets;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setPocketSize(double size) {
        this.pocketSize = size;
    }

    public double getPocketSize() {
        return pocketSize;
    }
}
