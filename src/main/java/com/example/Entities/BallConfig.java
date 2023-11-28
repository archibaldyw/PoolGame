package com.example.Entities;

import java.util.ArrayList;

public class BallConfig {
    ArrayList<String> color;
    ArrayList<Vector2D> position;
    double ballSpeed;
    double mass;

    public BallConfig() {
        color = new ArrayList<>();
        position = new ArrayList<>();
    }
    public ArrayList<String> getColor() {
        return color;
    }
    public ArrayList<Vector2D> getPosition() {
        return position;
    }
    public double getBallSpeed() {
        return ballSpeed;
    }

    public double getMass() {
        return mass;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setColor(ArrayList<String> color) {
        this.color = color;
    }

    public void setPosition(ArrayList<Vector2D> position) {
        this.position = position;
    }
}
