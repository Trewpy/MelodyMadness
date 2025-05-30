package com.melodymadness.game.model;

import java.util.ArrayList;

public class Lane {
    private int laneIndex;
    private ArrayList<Note> notes;

    public Lane(int laneIndex) {
        this.laneIndex = laneIndex;
        this.notes = new ArrayList<>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public int getLaneIndex() {
        return laneIndex;
    }

    public int handleKeyPress(double keyPressTime) {
        if (notes.size() == 0)
            return 0;
        Note firstNote = notes.get(0);
        double diff = keyPressTime - firstNote.getHitTime();
        String accuracy = HitData.getAccuracy(diff);
        int score = HitData.getScore(accuracy);
        if (!accuracy.equals("Miss")) {
            notes.remove(0);
        }
        return score;
    }
}