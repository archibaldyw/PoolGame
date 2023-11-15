package com.example.poolgame;

import javafx.application.Application;
import javafx.stage.Stage;

import com.example.Game.Game;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        // 初始化
        game.init(primaryStage);
        // 开始游戏
        game.playGame();
    }
}