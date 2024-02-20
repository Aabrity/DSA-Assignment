package com.example.que2;

import java.util.ArrayList;
import java.util.List;

public class que2b {
    // Method to find secrets based on intervals and first person's index
    public static List<Integer> findSecrets(int n, int[][] intervals, int firstPerson) {
        int[] counts = new int[n]; // Array to store counts of secrets

     
        for (int[] interval : intervals) {
         
            for (int i = interval[0]; i <= interval[1]; i++) {
                counts[i]++;
            }
        }

        List<Integer> result = new ArrayList<>(); // List to store secrets
       
        for (int i = 0; i < n; i++) {
            // If the secret is revealed in at least one interval, add it to the result list
            if (counts[i] > 0) {
                result.add(i);
            }
        }

        return result; // Return the list of revealed secrets
    }

    public static void main(String[] args) {
        int n = 5; 
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}}; 
        int firstPerson = 0; 

        // Find secrets based on intervals and first person's index
        List<Integer> result = findSecrets(n, intervals, firstPerson);
        System.out.println(result); 
    }
}
