package com.example.Game;

import com.example.Physics.CollisionPhysics;
import com.example.Physics.FrictionPhysics;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
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
import java.util.List;

public class Game {
    private static final int KEY_FRAME = 120;
    private static final double KEY_FRAME_DURATION = 1.0/KEY_FRAME;
    Stage primaryStage;
    private static final double BALL_SPEED = 0.2;
    private Group root;
    private ArrayList<Ball> allBalls;
    private Table table;
    private double mouseX;
    private double mouseY;
    private BallConfig ballConfig;
    private TableConfig tableConfig;
    private Ball selectedBall;
    private boolean isDragging;
    private Ball virtualBall;
    private Button bt_reset;
    private Button bt_rollback;
    private Button bt_save;
    private Button bt_easy;
    private Button bt_normal;
    private Button bt_hard;
    private int score = 0;
    private int time = 0;
    private final Text textScore = new Text(1000, 20, "Score: 0");
    private final Text textTime = new Text(1000, 50, "Time: 0");
    private int count = 0;
    private final ArrayList<Save> saves = new ArrayList<Save>();
    private ImageView cueStickImageView;


    public void init(Stage primaryStage) {

        root = new Group();
        Scene scene = new Scene(root);
        // 设置监听器
        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseDragged(this::handleMouseDragged);
        scene.setOnMouseReleased(this::handleMouseReleased);
        scene.setOnKeyPressed(this::handleKeyPressed);

        // 设置窗口
        primaryStage.setWidth(1200);
        primaryStage.setHeight(487);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PoolGame");
        primaryStage.show();
        this.primaryStage = primaryStage;

        // 读取配置文件 工厂模式
        ConfigReaderFactory factory = new ConfigReaderFactory();
        ConfigReader<BallConfig> ballConfigReader = factory.createConfigReader("ball");
        ConfigReader<TableConfig> tableConfigReader = factory.createConfigReader("table");
        tableConfig = tableConfigReader.readConfig("./table_config.json");
        ballConfig = ballConfigReader.readConfig("./easy_config.json");

        load();

    }

    public void initButton() {
        ConfigReaderFactory factory = new ConfigReaderFactory();
        ConfigReader<BallConfig> ballConfigReader = factory.createConfigReader("ball");
        ConfigReader<TableConfig> tableConfigReader = factory.createConfigReader("table");

        bt_easy = new Button("Easy");
        bt_easy.setLayoutX(1000);
        bt_easy.setLayoutY(100);
        bt_easy.setOnAction(e -> {
            tableConfig = tableConfigReader.readConfig("./table_config.json");
            ballConfig = ballConfigReader.readConfig("./easy_config.json");
            load();
        });

        bt_normal = new Button("Normal");
        bt_normal.setLayoutX(1000);
        bt_normal.setLayoutY(130);
        bt_normal.setOnAction(e -> {
            tableConfig = tableConfigReader.readConfig("./table_config.json");
            ballConfig = ballConfigReader.readConfig("./normal_config.json");
            load();
        });

        bt_hard = new Button("Hard");
        bt_hard.setLayoutX(1000);
        bt_hard.setLayoutY(160);
        bt_hard.setOnAction(e -> {
            tableConfig = tableConfigReader.readConfig("./table_config.json");
            ballConfig = ballConfigReader.readConfig("./hard_config.json");
            load();
        });

        bt_reset = new Button("Reset");
        bt_reset.setLayoutX(1000);
        bt_reset.setLayoutY(200);
        bt_reset.setOnAction(e -> {
            load();
        });

        bt_save = new Button("Save");
        bt_save.setLayoutX(1000);
        bt_save.setLayoutY(230);
        bt_save.setOnAction(e -> {
            save();
        });

        bt_rollback = new Button("Rollback");
        bt_rollback.setLayoutX(1000);
        bt_rollback.setLayoutY(260);
        bt_rollback.setOnAction(e -> {
            rollback();
        });

        root.getChildren().add(bt_easy);
        root.getChildren().add(bt_normal);
        root.getChildren().add(bt_hard);
        root.getChildren().add(bt_reset);
        root.getChildren().add(bt_save);
        root.getChildren().add(bt_rollback);
    }

    public void initText() {
        time = 0;
        score = 0;
        textTime.setText("Time: 0");
        textScore.setText("Score: 0");
        root.getChildren().add(textTime);
        root.getChildren().add(textScore);
    }

    public void load() {
        root.getChildren().clear();

        saves.clear();

        initButton();
        initText();

        // 创造Table
        TableBuilder tableBuilder = new TableBuilder(tableConfig);
        table = tableBuilder.initTable(root);

        // 创造Ball 建造者模式
        BallBuilder ballBuilder = new BallBuilder(ballConfig);
        // 虚拟球
        virtualBall = ballBuilder.buildVirtualBall(root);
        // 所有球的队列
        allBalls = ballBuilder.initBalls(root);

        Image cueStickImage = new Image("./cue_stick.png");
        cueStickImageView = new ImageView(cueStickImage);
        cueStickImageView.setFitWidth(cueStickImage.getWidth());
        cueStickImageView.setFitHeight(cueStickImage.getHeight());
        cueStickImageView.setVisible(false);
        root.getChildren().add(cueStickImageView);

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

        updateTime();
        updateScore();
        updateBalls();
        // 处理碰撞 包括球与球 球与边界
        handleCollision();
        // 处理进洞
        handleBallInPocket();
    }

    public void updateTime() {
        count++;
        if(count == KEY_FRAME) {
            time++;
            count = 0;
        }
        textTime.setText("Time: " + time);
    }

    public void updateScore() {
        textScore.setText("Score: " + score);
    }

    // 更新球的位置
    private void updateBalls() {
        double friction = table.getFriction();
        ArrayList<Circle> holes = table.getPockets();
        for (Ball ball : allBalls) {
            ball.setVelocity(FrictionPhysics.calculateVelocity(ball, friction));
            ball.update();
        }
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
        for(int i=0; i<allBalls.size(); i++) {
            Ball ball = allBalls.get(i);
            if(checkBallInPocket(ball)) {
                RemoveBallStrategy removeBallStrategy = new RemoveBallStrategy();
                ResetBallStrategy resetBallStrategy = new ResetBallStrategy();
                switch(ball.getColor()) {
                    case WHITE:
                        // 移除母球
                        removeBallStrategy.handleBallInPocket(root, ball, ballConfig);
                        // 窗口输出失败
                        showText("You Lose!");
                        break;
                    case RED:
                    case YELLOW:
                    case ORANGE:
                        // 移除红球
                        removeBallStrategy.handleBallInPocket(root, ball, ballConfig);
                        allBalls.remove(ball);
                        if(checkWin()) {
                            showText("You Win!");
                        }
                        score+=1;
                        break;
                    case BLUE:
                    case GREEN:
                    case PURPLE:
                    case BROWN:
                    case BLACK:
                        if(ball.getHP() > 1) {
                            // 重置球
                            resetBallStrategy.handleBallInPocket(root, ball, ballConfig);
                        }
                        else {
                            // 移除球
                            removeBallStrategy.handleBallInPocket(root, ball, ballConfig);
                            allBalls.remove(ball);
                            if(checkWin()) {
                                showText("You Win!");
                            }
                        }
                        score+=2;
                        break;
                }
            }
        }
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

    public void save() {
        ArrayList<Ball> saveBalls = new ArrayList<>();
        for(Ball b : allBalls) {
            saveBalls.add(new Ball(b));
        }
        saves.add(new Save(score, time, saveBalls));
    }

    public void rollback() {
        if(saves.size() > 0) {
            Save save = saves.get(saves.size()-1);
            score = save.getScore();
            time = save.getTime();
            for(int i=0; i<allBalls.size(); i++) {
                this.allBalls.get(i).setPos(save.getBalls().get(i).getPos());
                this.allBalls.get(i).setVelocity(save.getBalls().get(i).getVelocity());
            }
            saves.remove(saves.size()-1);
        }
    }


    // 拖拽球的函数
    private void handleMouseDragged(MouseEvent event) {
        // 如果正在拖拽 且 选中了球
        if (isDragging && selectedBall != null) {
            double tmpX = event.getSceneX();
            double tmpY = event.getSceneY();
            // 计算虚拟球的位置 并 显示虚拟球
            virtualBall.getCircle().setVisible(true);
            virtualBall.setPos(new Vector2D(tmpX, tmpY));

            this.cueStickImageView.setVisible(true);
            // 设置旋转角度
            double angle = Math.toDegrees(Math.atan2(this.selectedBall.getPos().y - tmpY,
                    this.selectedBall.getPos().x - tmpX));

            Rotate rotate = new Rotate();
            // 设置旋转角度
            rotate.setAngle(angle);
            rotate.setPivotX(tmpX);
            rotate.setPivotY(tmpY);
            cueStickImageView.getTransforms().clear();  // 清除之前的变换
            cueStickImageView.getTransforms().add(rotate);

            this.cueStickImageView.setX(tmpX - this.cueStickImageView.getFitWidth());
            this.cueStickImageView.setY(tmpY - this.cueStickImageView.getFitHeight() / 2);
        }
    }

    // 按下鼠标的函数
    private void handleMousePressed(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        //Ball ball = cueBall;    // 若开启作弊模式 则将此行注释掉

        for(Ball ball : allBalls) {     // 作弊模式 快速通关测试
            // 如果鼠标点击的位置在球的范围内 且 球的速度为0
            if (ball.contains(new Vector2D(mouseX, mouseY)) && ball.getVelocity().equals(new Vector2D(0, 0))) {
                mouseX = ball.getPos().x;
                mouseY = ball.getPos().y;
                selectedBall = ball;
                isDragging = true;
                return;
            }
        }
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

            this.cueStickImageView.setVisible(false);
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        // 如果按下了空格键
        if (event.getCode() == KeyCode.SPACE) {
            init(primaryStage);
        }
    }
}