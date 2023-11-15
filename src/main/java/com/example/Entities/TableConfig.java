package com.example.Entities;

public class TableConfig {
    private Vector2D size;
    private Vector3D color;
    private double friction;

    public Vector2D getSize() {
        return size;
    }

    public Vector3D getColor() {
        return color;
    }

    public double getFriction() {
        return friction;
    }

    public void setSize(Vector2D size) {
        this.size = size;
    }

    public void setColor(Vector3D color) {
        this.color = color;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }
}
