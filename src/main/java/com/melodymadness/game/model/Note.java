package com.melodymadness.game.model;

public class Note {
    private int lane;
    private double hitTime;
    private boolean isLong;
    private boolean hit = false; // NEW

    public Note(int lane, double hitTime, boolean isLong) {
        this.lane = lane;
        this.hitTime = hitTime;
        this.isLong = isLong;
    }

    public int getLane() {
        return lane;
    }

    public double getHitTime() {
        return hitTime;
    }

    public boolean isLong() {
        return isLong;
    }

    public boolean isHit(double keyPressTime) {
        double error = Math.abs(keyPressTime - hitTime);
        return error < 0.1;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }
}
