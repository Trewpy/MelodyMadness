package com.melodymadness.game.model;

public class TestScoring {
    public static void main(String[] args) {
        Song song = new Song();
        song.loadFromFile("test_song.txt");
        Lane lane = song.getLane(0);
        double keyPressTime = 2.15;
        int score = lane.handleKeyPress(keyPressTime);
        System.out.println("Score: " + score); // Simulated key press time
    }
}