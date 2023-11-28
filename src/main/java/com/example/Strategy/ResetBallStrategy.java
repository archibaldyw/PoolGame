package com.example.Strategy;

import com.example.Entities.Ball;
import com.example.Entities.BallColor;
import com.example.Entities.BallConfig;
import com.example.Entities.Vector2D;
import javafx.scene.Group;

public class ResetBallStrategy implements BallBehaviorStrategy{
    @Override
    public void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig) {
        BallColor.Color color = ball.getColor();
        int id = ball.getID();
        switch(color) {
            case WHITE:
            case RED:
            case YELLOW:
            case ORANGE:
                ball.setHP(ball.getHP()-1);
                break;
            case BLUE:
            case GREEN:
            case PURPLE:
            case BROWN:
            case BLACK:
                ball.setPos(ballConfig.getPosition().get(id));
                ball.setVelocity(new Vector2D(0, 0));
                ball.setHP(ball.getHP()-1);
                break;

        }
    }
}
