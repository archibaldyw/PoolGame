package com.example.Physics;

import com.example.Entities.Vector2D;
import com.example.Entities.Ball;

public class FrictionPhysics {

    // 根据摩擦力计算速度
    public static Vector2D calculateVelocity(Ball ball, double friction) {
        Vector2D velocity = ball.getVelocity();
        double v = ball.calculateVelocity();
        if(v > 0.05) {
            double unitX = velocity.x / v;
            double unitY = velocity.y / v;
            velocity.x -= friction * unitX;
            velocity.y -= friction * unitY;
        }
        else {
            velocity.x = 0;
            velocity.y = 0;
        }
        return velocity;
    }
}
