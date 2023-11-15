package com.example.Entities;

import java.util.ArrayList;

public class BallConfig {
    Vector3D cueBallColor;
    Vector2D cueBallPos;
    ArrayList<Vector2D> blueBallPos;
    ArrayList<Vector2D> redBallPos;
    double ballSpeed;
    double mass;


    public double getBallSpeed() {
        return ballSpeed;
    }

    public Vector3D getCueBallColor() {
        return cueBallColor;
    }

    public Vector2D getCueBallPos() {
        return cueBallPos;
    }

    public ArrayList<Vector2D> getBlueBallPos() {
        return blueBallPos;
    }

    public ArrayList<Vector2D> getRedBallPos() {
        return redBallPos;
    }

    public double getMass() {
        return mass;
    }

    public void setBallSpeed(double ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public void setCueBallColor(Vector3D cueBallColor) {
        this.cueBallColor = cueBallColor;
    }

    public void setCueBallPos(Vector2D cueBallPos) {
        this.cueBallPos = cueBallPos;
    }

    public void setBlueBallPos(ArrayList<Vector2D> blueBallPos) {
        this.blueBallPos = blueBallPos;
    }

    public void setRedBallPos(ArrayList<Vector2D> redBallPos) {
        this.redBallPos = redBallPos;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
