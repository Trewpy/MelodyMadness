package com.melodymadness.game;

import com.melodymadness.game.ui.StartScene;
import com.melodymadness.game.ui.SongMenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class FinalProject extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load custom font early
        Font.loadFont(getClass().getResource("/fonts/Satisfy-Regular.ttf").toExternalForm(), 12);

        // Show start screen FIRST
        Scene startScene = StartScene.createStartScene(primaryStage, () -> {
            // When "Start" is clicked → go to song menu
            Scene menuScene = SongMenuScene.createMenuScene(primaryStage);
            primaryStage.setScene(menuScene);
        });

        primaryStage.setScene(startScene);
        primaryStage.setTitle("Melody Madness");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
