package com.example.Strategy;
import com.example.Entities.Ball;
import com.example.Entities.BallConfig;
import javafx.scene.Group;

public class RemoveBallStrategy implements BallBehaviorStrategy{
    @Override
    public void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig) {
        root.getChildren().remove(ball.getCircle());
    }
}
