package com.example.Builder;

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

    // 读取配置文件，构建母球 母球编号0
    public Ball buildCueBall(Group root) {
        Ball ball = new Ball();
        ball.setColor(ballConfig.getCueBallColor().toColor());
        ball.setPos(ballConfig.getCueBallPos());
        ball.setID(0);
        ball.setHP(1);
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

    // 构建红球 红球标号为3-4
    public ArrayList<Ball> initRedBalls(Group root) {
        ArrayList<Ball> balls = new ArrayList<>();
        int blueNum = ballConfig.getBlueBallPos().size();
        int redNum = ballConfig.getRedBallPos().size();
        for(int i=blueNum+1; i<=1+blueNum+redNum-1; i++) {
            Ball ball = buildRedBall(root, i);
            balls.add(ball);
        }
        return balls;
    }

    // 构建所有蓝球 蓝球标号为1-2
    public ArrayList<Ball> initBlueBalls(Group root) {
        ArrayList<Ball> balls = new ArrayList<>();
        int blueNum = ballConfig.getBlueBallPos().size();
        for(int i=1; i<=1+blueNum-1; i++) {
            Ball ball = buildBlueBall(root, i);
            balls.add(ball);
        }
        return balls;
    }
    public Ball buildBlueBall(Group root, int num) {
        Ball ball = new Ball();
        ball.setColor(Color.BLUE);
        ball.setPos(ballConfig.getBlueBallPos().get(num-1));
        ball.setID(num);
        ball.setHP(1);
        root.getChildren().add(ball.getCircle());
        return ball;
    }
    public Ball buildRedBall(Group root, int num) {
        Ball ball = new Ball();
        ball.setColor(Color.RED);
        ball.setPos(ballConfig.getRedBallPos().get(num-3));
        ball.setID(num);
        root.getChildren().add(ball.getCircle());
        return ball;
    }
}
