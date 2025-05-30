package com.melodymadness.game.model;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Song {
    private Lane[] lanes;
    private int totalScore;

    public Song() {
        lanes = new Lane[4];
        for (int i = 0; i < 4; i++) {
            lanes[i] = new Lane(i);
        }
    }

    public Lane getLane(int index) {
        return lanes[index];
    }

    public void addNote(Note note) {
        lanes[note.getLane()].addNote(note);
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> allNotes = new ArrayList<>();
        for (Lane lane : lanes) {
            allNotes.addAll(lane.getNotes());
        }
        return allNotes;
    }

    public void loadFromFile(String filename) {
        try {
            Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String[] parts = scan.nextLine().split(",");
                int lane = Integer.parseInt(parts[0]);
                double time = Double.parseDouble(parts[1]);
                Note note = new Note(lane, time, false);
                addNote(note);

            }
            scan.close();
        } catch (IOException e) {
            System.out.println("Error loading song: " + e.getMessage());
        }
    }

    public void saveToFile(String filename) throws IOException {
        try {
            PrintWriter writer = new PrintWriter(filename);
            for (Note note : getAllNotes()) {
                writer.println(note.getLane() + "," + note.getHitTime());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving song: " + e.getMessage());
        }
    }

    public void loadFromReader(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split(",");
            if (parts.length == 2) {
                int lane = Integer.parseInt(parts[0]);
                double time = Double.parseDouble(parts[1]);
                Note note = new Note(lane, time, false);
                addNote(note);
            }
        }
    }

    public int getScore() {
        return totalScore;
    }

}
