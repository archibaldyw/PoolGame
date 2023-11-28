package com.example.Builder;

import com.example.Entities.Vector2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import com.example.Entities.Ball;
import com.example.Entities.BallConfig;

import java.util.ArrayList;

public class BallBuilder {
    private BallConfig ballConfig;    // 读取的配置
    public BallBuilder(BallConfig ballconfig) {
        this.ballConfig = ballconfig;
    }

    public ArrayList<Ball> initBalls(Group root) {
        ArrayList<Ball> balls = new ArrayList<>();
        int n = ballConfig.getPosition().size();
        for(int i=0; i<n; i++) {
            Ball ball = buildBall(root, i);
            balls.add(ball);
        }
        return balls;
    }

    public Ball buildBall(Group root, int num) {
        Ball ball = new Ball();
        ball.setColor(ballConfig.getColor().get(num));
        ball.setPos(ballConfig.getPosition().get(num));
        ball.setID(num);
        ball.initHP();
        root.getChildren().add(ball.getCircle());
        return ball;
    }

    // 为了实现拖拽效果，需要构建一个虚拟的球
    public Ball buildVirtualBall(Group root) {
        Ball ball = new Ball();
        ball.getCircle().setFill(Color.TRANSPARENT);
        ball.getCircle().setStroke(Color.BLACK);
        ball.getCircle().setVisible(false);
        root.getChildren().add(ball.getCircle());
        return ball;
    }

}
