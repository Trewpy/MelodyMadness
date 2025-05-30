package com.melodymadness.game.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SongMenuScene {

    public static Scene createMenuScene(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("SONGS");

        // Load custom font
        try {
            Font customFont = Font.loadFont(
                    SongMenuScene.class.getResource("/fonts/Satisfy-Regular.ttf").toExternalForm(), 40);
            if (customFont != null) {
                title.setFont(customFont);
            }
        } catch (Exception e) {
            System.out.println("Failed to load font: " + e.getMessage());
        }

        root.getChildren().add(title);

        // List all your available songs here
        String[] songs = {
                "safeandsoundtest.txt",
                "test_sonicblaster.txt"
        };

        for (String file : songs) {
            String displayName = file.replace(".txt", "").replace("_", " ");

            Button songButton = new Button(displayName);
            songButton.setOnAction(e -> {
                GamePlayScene game = new GamePlayScene(primaryStage, "/songs/" + file);
                primaryStage.setScene(game.getScene());
            });

            root.getChildren().add(songButton);
        }

        return new Scene(root, 800, 600);
    }
}
