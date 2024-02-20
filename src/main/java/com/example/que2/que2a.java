package com.example.que2;

import java.util.Arrays;

public class que2a {
    // Method to calculate the minimum moves
    public static int minMovesToEqualizeDresses(int[] sewingMachines) {
        // Calculate the total number of dresses across all sewing machines
        int totalDresses = Arrays.stream(sewingMachines).sum();
        int numMachines = sewingMachines.length;

        if (totalDresses % numMachines != 0) {
            return -1;
        }

        // Calculate the target number of dresses that each machine should have
        int targetDresses = totalDresses / numMachines;
        int moves = 0;
        int cumulativeDresses = 0;

        // Iterate through each machine's dresses
        for (int dresses : sewingMachines) {
            cumulativeDresses += dresses - targetDresses;
            // Update the maximum moves required among all machines
            moves = Math.max(moves, Math.abs(cumulativeDresses));
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] sewingMachines = { 1, 0, 5 };
        System.out.println(minMovesToEqualizeDresses(sewingMachines)); // Output: 3 (minimum moves required)
    }
}
