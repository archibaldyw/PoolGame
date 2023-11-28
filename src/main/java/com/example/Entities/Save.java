package com.example.Entities;

import java.util.ArrayList;

public class Save {
    private int score;
    private int time;
    private ArrayList<Ball> balls;

    public Save(int score, int time, ArrayList<Ball> balls) {
        this.score = score;
        this.time = time;
        this.balls = balls;
    }
    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setBalls(ArrayList<Ball> balls) {
        this.balls = balls;
    }
}
