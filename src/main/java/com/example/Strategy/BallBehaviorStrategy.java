package com.example.Strategy;
import com.example.Entities.Ball;
import com.example.Entities.BallConfig;
import javafx.scene.Group;

public interface BallBehaviorStrategy {
    void handleBallInPocket(Group root, Ball ball, BallConfig ballConfig);
}
