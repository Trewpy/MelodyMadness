package com.melodymadness.game.model;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class SongRecorder {
    private static Song song = new Song();
    private static long startTime;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Song Recorder - Press D/F/J/K");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);

        JLabel label = new JLabel("Press D/F/J/K to record notes, ESC to save & exit.", SwingConstants.CENTER);
        frame.add(label);

        startTime = System.currentTimeMillis();

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int lane = -1;

                if (key == KeyEvent.VK_D)
                    lane = 0;
                else if (key == KeyEvent.VK_F)
                    lane = 1;
                else if (key == KeyEvent.VK_J)
                    lane = 2;
                else if (key == KeyEvent.VK_K)
                    lane = 3;
                else if (key == KeyEvent.VK_ESCAPE) {
                    try {
                        song.saveToFile("src/main/resources/songs/recorded_song.txt");
                        System.out.println("\n Recording saved to recorded_song.txt");
                    } catch (IOException ex) {
                        System.out.println("Failed to save song: " + ex.getMessage());
                    }
                    System.exit(0);
                }

                if (lane != -1) {
                    System.out.println("lane: " + lane);
                    double time = (System.currentTimeMillis() - startTime) / 1000.0;
                    song.addNote(new Note(lane, time, false));
                    System.out.printf("Recorded: Lane %d at %.2fs\n", lane, time);
                }
            }
        });

        frame.setVisible(true);

    }
}