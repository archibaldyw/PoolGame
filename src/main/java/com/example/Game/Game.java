package com.example.Game;

import com.example.Physics.CollisionPhysics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import com.example.Builder.BallBuilder;
import com.example.Strategy.RemoveBallStrategy;
import com.example.Strategy.ResetBallStrategy;
import com.example.Config.ConfigReaderFactory;
import com.example.Config.ConfigReader;
import com.example.Entities.*;
import com.example.Builder.TableBuilder;

import javafx.util.Pair;
import java.util.ArrayList;

public class Game {
    private static final double KEY_FRAME_DURATION = 1.0/120.0;
    Stage primaryStage;
    private static final double BALL_SPEED = 0.2;
    private Group root;
    private ArrayList<Ball> allBalls;
    private ArrayList<Ball> blueBalls;
    private ArrayList<Ball> redBalls;
    private Ball cueBall;
    private Table table;
    private double mouseX;
    private double mouseY;
    private BallConfig ballConfig;
    private TableConfig tableConfig;
    private Ball selectedBall;
    private boolean isDragging;
    private Ball virtualBall;


    public void init(Stage primaryStage) {

        root = new Group();
        Scene scene = new Scene(root);
        // 设置监听器
        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);
        scene.setOnMouseReleased(this::handleMouseReleased);
        scene.setOnKeyPressed(this::handleKeyPressed);

        // 设置窗口
        primaryStage.setWidth(914);
        primaryStage.setHeight(487);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFXDemo");
        primaryStage.show();
        this.primaryStage = primaryStage;

        // 读取配置文件 工厂模式
        ConfigReaderFactory factory = new ConfigReaderFactory();
        ConfigReader<BallConfig> ballConfigReader = factory.createConfigReader("ball");
        ConfigReader<TableConfig> tableConfigReader = factory.createConfigReader("table");
        tableConfig = tableConfigReader.readConfig("./table_config.json");
        ballConfig = ballConfigReader.readConfig("./ball_config.json");

        // 创造Table
        TableBuilder tableBuilder = new TableBuilder(tableConfig);
        table = tableBuilder.initTable(root);

        // 创造Ball 建造者模式
        BallBuilder ballBuilder = new BallBuilder(ballConfig);
        // 母球
        cueBall = ballBuilder.buildCueBall(root);
        // 红篮球
        redBalls = ballBuilder.initRedBalls(root);
        blueBalls = ballBuilder.initBlueBalls(root);
        // 虚拟球
        virtualBall = ballBuilder.buildVirtualBall(root);
        // 所有球的队列
        allBalls = new ArrayList<Ball>();
        allBalls.add(cueBall);
        allBalls.addAll(blueBalls);
        allBalls.addAll(redBalls);
    }

    public void playGame() {
        // 设置循环
        Timeline animationLoop = new Timeline();
        animationLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(KEY_FRAME_DURATION), (actionEvent) -> updateGame());
        animationLoop.getKeyFrames().add(frame);
        animationLoop.play();
    }
    public void updateGame() {
        // 更新游戏

        // 更新求的位置
        updateBalls();
        // 处理碰撞 包括球与球 球与边界
        handleCollision();
        // 处理进洞
        handleBallInPocket();
    }

    // 检测球与球是否发生碰撞的函数
    public boolean checkBallCollision(Ball ball1, Ball ball2) {
        double distance = ball1.distance(ball2);
        return distance <= ball1.getCircle().getRadius() + ball2.getCircle().getRadius();
    }

    public boolean checkBallInPocket(Ball ball) {
        ArrayList<Circle> pockets = table.getPockets();
        for(Circle pocket : pockets) {
            Ball tmpBall = new Ball();
            tmpBall.setCircle(pocket);
            if(tmpBall.contains(ball.getPos())) {
                return true;
            }
        }
        return false;
    }
    public boolean checkWin() {
        return allBalls.size() == 1 && allBalls.get(0).getID() == 0;
    }
    public void showText(String s) {
        Text text = new Text(s);
        text.setFont(new Font(30));
        text.setX(400);
        text.setY(200);
        root.getChildren().add(text);
    }
    // 处理碰撞的函数
    public void handleCollision() {
        // 球与球的碰撞
        for(int i=0; i<allBalls.size(); i++) {
            for(int j=i+1; j<allBalls.size(); j++) {
                if(checkBallCollision(allBalls.get(i), allBalls.get(j))) {
                    Ball ball1 = allBalls.get(i);
                    Ball ball2 = allBalls.get(j);
                    // 计算球体碰撞 返回两个物体的速度
                    Pair<Vector2D, Vector2D> result = CollisionPhysics.calculateBallCollision(
                            ball1.getPos(),
                            ball1.getVelocity(),
                            ballConfig.getMass(),
                            ball2.getPos(),
                            ball2.getVelocity(),
                            ballConfig.getMass()
                    );
                    allBalls.get(i).setVelocity(result.getKey());
                    allBalls.get(j).setVelocity(result.getValue());
                }
            }
        }
        // 球与边界的碰撞
        for (Ball ball : allBalls) {
            // 计算边界碰撞 返回速度
            Vector2D v = CollisionPhysics.calculateEdgeCollision(ball, table);
            ball.setVelocity(v);
        }
    }

    // 处理球进洞 顺带判断游戏结束或者失败
    public void handleBallInPocket() {
        ArrayList<Circle> holes = table.getPockets();
        int blueNum = ballConfig.getBlueBallPos().size();
        int redNum = ballConfig.getRedBallPos().size();
        for(int i=0; i<allBalls.size(); i++) {
            Ball ball = allBalls.get(i);
            if(checkBallInPocket(ball)) {
                // 如果是母球
                if(ball.getID() == 0) {
                    if(ball.getHP() > 0) {
                        // 重置母球
                        ResetBallStrategy resetBallStrategy = new ResetBallStrategy();
                        resetBallStrategy.handleBallInPocket(root, ball, ballConfig);
                    }
                    else {
                        // 移除母球
                        RemoveBallStrategy removeBallStrategy = new RemoveBallStrategy();
                        removeBallStrategy.handleBallInPocket(root, ball, ballConfig);
                        // 窗口输出失败
                        showText("You Lose!");
                    }

                }
                // 如果是红球
                else if(ball.getID() >= 1+blueNum && ball.getID() <= 1+blueNum+redNum-1) {
                    // 移除红球
                    RemoveBallStrategy removeBallStrategy = new RemoveBallStrategy();
                    removeBallStrategy.handleBallInPocket(root, ball, ballConfig);
                    allBalls.remove(ball);
                    if(checkWin()) {
                        showText("You Win!");
                    }
                }
                // 如果是蓝球
                else if(ball.getID() >= 1 && ball.getID() <= 1+blueNum-1) {
                    if(ball.getHP() > 0) {
                        // 重置蓝球
                        ResetBallStrategy resetBallStrategy = new ResetBallStrategy();
                        resetBallStrategy.handleBallInPocket(root, ball, ballConfig);
                    }
                    else {
                        // 移除蓝球
                        RemoveBallStrategy removeBallStrategy = new RemoveBallStrategy();
                        removeBallStrategy.handleBallInPocket(root, ball, ballConfig);
                        allBalls.remove(ball);
                        if(checkWin()) {
                            showText("You Win!");
                        }
                    }

                }
            }
        }
    }
    // 更新球的位置
    private void updateBalls() {
        double friction = table.getFriction();
        ArrayList<Circle> holes = table.getPockets();
        for (Ball ball : allBalls) {
            ball.update(friction);
        }
    }

    // 拖拽球的函数
    private void handleMouseDragged(MouseEvent event) {
        // 如果正在拖拽 且 选中了球
        if (isDragging && selectedBall != null) {
            double tmpX = event.getX();
            double tmpY = event.getY();
            // 计算虚拟球的位置 并 显示虚拟球
            virtualBall.getCircle().setVisible(true);
            virtualBall.setPos(new Vector2D(tmpX, tmpY));
        }
    }

    // 按下鼠标的函数
    private void handleMousePressed(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        Ball ball = cueBall;    // 若开启作弊模式 则将此行注释掉

        //for(Ball ball : allBalls) {     // 作弊模式 快速通关测试
            // 如果鼠标点击的位置在球的范围内 且 球的速度为0
            if (ball.contains(new Vector2D(mouseX, mouseY)) && ball.getVelocity().equals(new Vector2D(0, 0))) {
                mouseX = ball.getPos().x;
                mouseY = ball.getPos().y;
                selectedBall = ball;
                isDragging = true;
                return;
            }
        //}

    }

    // 松开鼠标的函数
    private void handleMouseReleased(MouseEvent event) {
        // 如果正在拖拽 且 选中了球
        if (isDragging && selectedBall != null) {
            // 隐藏虚拟球
            virtualBall.getCircle().setVisible(false);

            double deltaX =  mouseX - event.getX();
            double deltaY = mouseY - event.getY();

            // 根据拖拽的距离计算速度 和 角度
            double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY) * BALL_SPEED;
            double angle = Math.atan2(deltaY, deltaX);

            // 设置球的速度
            selectedBall.setVelocity(new Vector2D(speed * Math.cos(angle), speed * Math.sin(angle)));

            isDragging = false;
            selectedBall = null;
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        // 如果按下了空格键
        if (event.getCode() == KeyCode.SPACE) {
            init(primaryStage);
        }
    }

}