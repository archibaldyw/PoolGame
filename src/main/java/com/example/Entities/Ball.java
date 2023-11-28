package com.example.Entities;

import com.example.Physics.FrictionPhysics;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
public class Ball {
    private Circle circle;
    private Vector2D velocity;
    private int HP;
    private BallColor.Color color;
    public Ball() {
        circle = new Circle(0, 0, 18);
        velocity = new Vector2D(0, 0);
        HP = 0;
    }

    public Ball(Ball ball) {
        circle = new Circle(ball.getCircle().getCenterX(), ball.getCircle().getCenterY(), 18);
        velocity = new Vector2D(ball.getVelocity().x, ball.getVelocity().y);
        HP = ball.getHP();
        color = ball.getColor();
        circle.setFill(ball.getCircle().getFill());
    }
    public boolean contains(Vector2D pos) {
        return circle.contains(pos.x, pos.y);
    }

    // 更新球的位置
    public void update() {
        setPos(getPos().add(velocity));
    }
    public double calculateVelocity() {
        double v = Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y);
        return v;
    }
    public double distance(Ball ball) {
        return Math.sqrt(Math.pow(ball.getPos().x - getPos().x, 2) + Math.pow(ball.getPos().y - getPos().y, 2));
    }
    public int getHP() { return HP; }
    public Circle getCircle() {
        return circle;
    }
    public Vector2D getPos() {
        return new Vector2D(circle.getCenterX(), circle.getCenterY());
    }
    public int getID() { return Integer.parseInt(circle.getId()); }
    public BallColor.Color getColor() {
        return this.color;
    }
    public void setColor(String color) {
        switch(color) {
            case "WHITE":
                this.color = BallColor.Color.WHITE;
                circle.setFill(Color.WHITE);
                break;
            case "RED":
                this.color = BallColor.Color.RED;
                circle.setFill(Color.RED);
                break;
            case "BLUE":
                this.color = BallColor.Color.BLUE;
                circle.setFill(Color.BLUE);
                break;
            case "ORANGE":
                this.color = BallColor.Color.ORANGE;
                circle.setFill(Color.ORANGE);
                break;
            case "YELLOW":
                this.color = BallColor.Color.YELLOW;
                circle.setFill(Color.YELLOW);
                break;
            case "GREEN":
                this.color = BallColor.Color.GREEN;
                circle.setFill(Color.GREEN);
                break;
            case "PURPLE":
                this.color = BallColor.Color.PURPLE;
                circle.setFill(Color.PURPLE);
                break;
            case "BROWN":
                this.color = BallColor.Color.BROWN;
                circle.setFill(Color.BROWN);
                break;
            case "BLACK":
                this.color = BallColor.Color.BLACK;
                circle.setFill(Color.BLACK);
                break;
        }
    }
    public void setPos(Vector2D pos) {
        circle.setCenterX(pos.x);
        circle.setCenterY(pos.y);
    }
    public void setID(int num) { circle.setId(Integer.toString(num)); }
    public Vector2D getVelocity() {
        return velocity;
    }
    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    public void setCircle(Circle circle) {
        this.circle = circle;
    }
    public void initHP() {
        switch(this.color) {
            case WHITE:
                this.HP = 1;
                break;
            case RED:
                this.HP = 1;
                break;
            case BLUE:
                this.HP = 2;
                break;
            case ORANGE:
                this.HP = 1;
                break;
            case YELLOW:
                this.HP = 1;
                break;
            case GREEN:
                this.HP = 2;
                break;
            case PURPLE:
                this.HP = 2;
                break;
            case BROWN:
                this.HP = 3;
                break;
            case BLACK:
                this.HP = 3;
                break;
        }
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

}
