package com.melodymadness.game.model;

public class HitData {
    public static String getAccuracy(double timeDifference) {
        timeDifference = Math.abs(timeDifference);

        if (timeDifference < 0.1) {
            return "Perfect";
        } else if (timeDifference < 0.2) {
            return "Great";
        } else if (timeDifference < 0.3) {
            return "Good";
        } else {
            return "Miss";
        }
    }

    public static int getScore(String accuracy) {
        if (accuracy.equals("Perfect")) {
            return 3;
        } else if (accuracy.equals("Great")) {
            return 2;
        } else if (accuracy.equals("Good")) {
            return 1;
        } else if (accuracy.equals("Miss")) {
            return 0;
        } else {
            return 0;
        }
    }
}