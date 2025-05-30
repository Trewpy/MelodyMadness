package com.melodymadness.game.ui;

import com.melodymadness.game.model.Note;
import com.melodymadness.game.model.Song;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class GamePlayScene {

    private final Pane root;
    private final Scene scene;
    private final Song song;
    private final String songPath;
    private final int hitY = 750;
    private int score = 0;
    private Label scoreLabel;

    public GamePlayScene(Stage stage, String songPath) {
        this.songPath = songPath;
        this.root = new Pane();
        this.scene = new Scene(root, 600, 800);
        this.song = new Song();

        Label title = new Label("GamePlayScene: Notes to Hit");
        title.setLayoutX(200);
        title.setLayoutY(20);
        root.getChildren().add(title);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(50);
        root.getChildren().add(scoreLabel);

        // Draw lane lines
        for (int i = 0; i < 4; i++) {
            Line laneLine = new Line();
            laneLine.setStartX(laneToX(i) + 25);
            laneLine.setStartY(0);
            laneLine.setEndX(laneToX(i) + 25);
            laneLine.setEndY(800);
            laneLine.setStroke(Color.GRAY);
            root.getChildren().add(laneLine);
        }

        // Draw hit bar
        Line hitBar = new Line(0, hitY + 20, 600, hitY + 20);
        hitBar.setStroke(Color.RED);
        hitBar.setStrokeWidth(3);
        root.getChildren().add(hitBar);

        try {
            InputStream inputStream = getClass().getResourceAsStream(songPath);
            if (inputStream == null) {
                throw new RuntimeException("The song was not found in resources!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            song.loadFromReader(reader);
            System.out.println("Loaded " + song.getAllNotes().size() + " notes.");
        } catch (Exception e) {
            root.getChildren().add(new Label("Failed to load song: " + e.getMessage()));
            stage.setScene(scene);
            stage.show();
            return;
        }

        try {
            String musicFileName = switch (songPath) {
                case "/songs/safeandsoundtest.txt" -> "/music/safeandsound.mp3";
                case "/songs/Blue.txt" -> "/music/blue.mp3";
                case "/songs/SonicBlaster.txt" -> "/music/sonicblaster.mp3";
                default -> null;
            };
            if (musicFileName != null) {
                URL musicURL = getClass().getResource(musicFileName);
                if (musicURL != null) {
                    Media media = new Media(musicURL.toExternalForm());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                } else {
                    System.out.println(" Music file not found: " + musicFileName);
                }
            } else {
                System.out.println(" No music mapping for song path: " + songPath);
            }
        } catch (Exception e) {
            System.out.println(" Error playing music: " + e.getMessage());
        }

        // Spawn falling notes
        double noteFallTime = 2.0;
        Timeline spawnTimeline = new Timeline();

        for (Note note : song.getAllNotes()) {
            double spawnTime = Math.max(note.getHitTime() - noteFallTime, 0);
            double finalSpawnTime = spawnTime;

            KeyFrame spawnFrame = new KeyFrame(Duration.seconds(finalSpawnTime), event -> {
                Rectangle noteRect = new Rectangle(50, 20);
                noteRect.setX(laneToX(note.getLane()));
                noteRect.setY(0);
                noteRect.setFill(Color.DEEPSKYBLUE);
                noteRect.setUserData(note);
                root.getChildren().add(noteRect);
                System.out.println("Spawned note at lane " + note.getLane() + " @ " + finalSpawnTime + "s");
            });

            spawnTimeline.getKeyFrames().add(spawnFrame);
        }

        // Animate note fall
        Timeline animationTimeline = new Timeline(
                new KeyFrame(Duration.millis(16), e -> {
                    for (var node : root.getChildren()) {
                        if (node instanceof Rectangle rect) {
                            rect.setY(rect.getY() + 2);
                        }
                    }
                }));
        animationTimeline.setCycleCount(Animation.INDEFINITE);

        // Handle key press
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.D)
                checkHit(0);
            if (code == KeyCode.F)
                checkHit(1);
            if (code == KeyCode.J)
                checkHit(2);
            if (code == KeyCode.K)
                checkHit(3);
        });

        // Delay before game over
        double lastHitTime = song.getAllNotes().stream()
                .mapToDouble(Note::getHitTime)
                .max()
                .orElse(0);
        double gameOverTime = lastHitTime + 7.0;

        Timeline endTimeline = new Timeline(
                new KeyFrame(Duration.seconds(gameOverTime), e -> {
                    Scene gameOverScene = GameOverScene.createGameOverScene(stage, score, songPath);
                    stage.setScene(gameOverScene);
                }));
        endTimeline.play();

        stage.setScene(scene);
        stage.show();
        spawnTimeline.play();
        animationTimeline.play();
    }

    private void checkHit(int lane) {
        double tolerancePerfect = 50;
        double toleranceGood = 150;

        for (int i = 0; i < root.getChildren().size(); i++) {
            var node = root.getChildren().get(i);
            if (node instanceof Rectangle rect) {
                Note note = (Note) rect.getUserData();
                if (note != null && note.getLane() == lane && !note.isHit()) {
                    double noteY = rect.getY();
                    double diff = Math.abs(noteY - hitY);
                    if (diff <= toleranceGood) {
                        int points = diff <= tolerancePerfect ? 300 : 150;
                        note.setHit(true);
                        root.getChildren().remove(rect);
                        score += points;
                        scoreLabel.setText("Score: " + score);
                        return; // only hit one note
                    }
                }
            }
        }
    }

    private double laneToX(int lane) {
        return 100 + lane * 100;
    }

    public Scene getScene() {
        return scene;
    }
}
