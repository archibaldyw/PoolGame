package com.example.Physics;

import com.example.Entities.Vector2D;

import com.example.Entities.Ball;
import com.example.Entities.Table;

import javafx.scene.shape.Circle;
import javafx.util.Pair;

public class CollisionPhysics {

    // 计算两个球的碰撞
    public static Pair<Vector2D, Vector2D> calculateBallCollision(Vector2D positionA, Vector2D velocityA, double massA, Vector2D positionB, Vector2D velocityB, double massB) {
        Vector2D collisionVector = positionA.subtract(positionB);
        collisionVector = collisionVector.normalize();
        double vA = collisionVector.dotProduct(velocityA);
        double vB = collisionVector.dotProduct(velocityB);
        if (vB <= 0 && vA >= 0) {
            return new Pair<>(velocityA, velocityB);
        }
        double optimizedP = (2.0 * (vA - vB)) / (massA + massB);
        Vector2D velAPrime = velocityA.subtract(collisionVector.multiply(optimizedP).multiply(massB));
        Vector2D velBPrime = velocityB.add(collisionVector.multiply(optimizedP).multiply(massA));
        return new Pair<>(velAPrime, velBPrime);
    }

    // 计算球和边界之间的碰撞
    public static Vector2D calculateEdgeCollision(Ball ball, Table table) {
        Circle circle = ball.getCircle();
        Vector2D v = ball.getVelocity();
        double r = circle.getRadius();
        Vector2D pos = ball.getPos();
        if (circle.getCenterX() - r < 0) {
            v = new Vector2D(-ball.getVelocity().x, ball.getVelocity().y);
            ball.setPos(new Vector2D(r, pos.y));
        }
        if (circle.getCenterX() + r > table.getSize().x) {
            v = new Vector2D(-ball.getVelocity().x, ball.getVelocity().y);
            ball.setPos(new Vector2D(table.getSize().x - r, pos.y));
        }
        if (circle.getCenterY() - r < 0) {
            v = new Vector2D(ball.getVelocity().x, -ball.getVelocity().y);
            ball.setPos(new Vector2D(pos.x, r));
        }
        if (circle.getCenterY() + r > table.getSize().y) {
            v = new Vector2D(ball.getVelocity().x, -ball.getVelocity().y);
            ball.setPos(new Vector2D(pos.x, table.getSize().y - r));
        }
        return v;
    }
}
