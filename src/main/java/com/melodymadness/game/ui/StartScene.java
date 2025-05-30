package com.melodymadness.game.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StartScene {

    public static Scene createStartScene(Stage primaryStage, Runnable startAction) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));

        Label title = new Label("Melody Madness");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;");

        try {
            Font customFont = Font.loadFont(
                    StartScene.class.getResource("/fonts/Satisfy-Regular.ttf").toExternalForm(), 48);
            if (customFont != null) {
                title.setFont(customFont);
            }
        } catch (Exception e) {
            System.out.println(" Failed to load font: " + e.getMessage());
        }

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startAction.run()); // Use passed-in action

        root.getChildren().addAll(title, startButton);
        return new Scene(root, 800, 600);
    }
}
