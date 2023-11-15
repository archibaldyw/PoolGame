package com.example.Entities;

import javafx.scene.paint.Color;
public class Vector3D {
    public double x;
    public double y;
    public double z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Color toColor() {
        return Color.color(x, y, z);
    }

}
