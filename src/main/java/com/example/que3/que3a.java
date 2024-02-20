package com.example.que3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class que3a {
    private List<Double> scores; // List to store scores

    public que3a() {
        scores = new ArrayList<>(); // Initialize the list of scores
    }

    // Method to add a score to the list
    public void addScore(double score) {
        scores.add(score);
    }

    // Method to calculate the median score
    public double getMedianScore() {
        int size = scores.size(); 
        if (size == 0) {
            throw new IllegalStateException("No scores added yet"); 
        }

        Collections.sort(scores); // Sort the scores

        if (size % 2 == 0) { 
            int midIndex1 = size / 2 - 1; 
            int midIndex2 = size / 2; 
            double median1 = scores.get(midIndex1); 
            double median2 = scores.get(midIndex2); 
            return (median1 + median2) / 2.0; 
        } else { 
            int midIndex = size / 2; 
            return scores.get(midIndex); 
        }
    }

    public static void main(String[] args) {
        que3a scoreTracker = new que3a(); 
        scoreTracker.addScore(85.5); 
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1); 

        scoreTracker.addScore(81.2); 
        scoreTracker.addScore(88.7);
        double median2 = scoreTracker.getMedianScore(); 
        System.out.println("Median 2: " + median2); 
    }
}
