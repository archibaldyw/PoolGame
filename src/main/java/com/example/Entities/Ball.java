package com.example.Entities;

import com.example.Physics.FrictionPhysics;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Ball {
    private Circle circle;
    private Vector2D velocity;
    private int HP;
    public Ball() {
        circle = new Circle(0, 0, 18);
        velocity = new Vector2D(0, 0);
        HP = 0;
    }
    public boolean contains(Vector2D pos) {
        return circle.contains(pos.x, pos.y);
    }

    // 更新球的位置
    public void update(double friction) {
        velocity = FrictionPhysics.calculateVelocity(this, friction);
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
    public Paint getColor() {
        return circle.getFill();
    }
    public void setColor(Paint color) {
        circle.setFill(color);
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
    public void setHP(int HP) { this.HP = HP; }

}
