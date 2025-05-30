package com.melodymadness.game.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Scene scene = StartScene.createStartScene(primaryStage, this::startGame);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Melody Madness");
        primaryStage.show();
    }

    private void showStartScene() {
        Scene scene = StartScene.createStartScene(primaryStage, this::startGame);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Melody Madness");
        primaryStage.show();
    }

    private void showGamePlayScene() {
        // Temporary: hardcoded song path
        new GamePlayScene(primaryStage, "/songs/test_sonicblaster.txt");
    }

    public void showGameOverScene(int score) {
        // Must match the method signature: (Stage, int, String)
        Scene scene = GameOverScene.createGameOverScene(
                primaryStage,
                score,
                "/songs/test_sonicblaster.txt" // match the song you used earlier
        );
        primaryStage.setScene(scene);
    }

    public void startGame() {
        showGamePlayScene();
    }

    public void gameOver(int score) {
        showGameOverScene(score);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
