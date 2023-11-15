package com.example.Strategy;

import com.example.Entities.Ball;
import com.example.Entities.BallConfig;
import com.example.Entities.Vector2D;
import javafx.scene.Group;

public class ResetBallStrategy implements BallBehaviorStrategy{
    @Override
    public void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig) {
        int id = ball.getID();
        if(id == 0) {
            ball.setPos(ballConfig.getCueBallPos());
            ball.setVelocity(new Vector2D(0, 0));
            ball.setHP(ball.getHP()-1);
        }
        else {
            Vector2D pos = ballConfig.getBlueBallPos().get(id-1);
            ball.setPos(pos);
            ball.setVelocity(new Vector2D(0, 0));
            ball.setHP(ball.getHP()-1);
        }
    }
}
