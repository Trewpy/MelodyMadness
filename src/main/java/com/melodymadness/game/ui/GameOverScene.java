package com.melodymadness.game.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverScene {

    public static Scene createGameOverScene(Stage primaryStage, int score, String songPath) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Game Over");
        title.setStyle("-fx-font-size: 32px;");

        Label scoreLabel = new Label("Your Score: " + score);
        scoreLabel.setStyle("-fx-font-size: 24px;");

        // Replay the same song
        Button replayButton = new Button("Replay");
        replayButton.setOnAction(e -> {
            GamePlayScene game = new GamePlayScene(primaryStage, songPath);
            primaryStage.setScene(game.getScene());
        });

        // Return to menu
        Button menuButton = new Button("Return to Menu");
        menuButton.setOnAction(e -> {
            Scene menu = SongMenuScene.createMenuScene(primaryStage);
            primaryStage.setScene(menu);
        });

        root.getChildren().addAll(title, scoreLabel, replayButton, menuButton);
        return new Scene(root, 800, 600);
    }
}
