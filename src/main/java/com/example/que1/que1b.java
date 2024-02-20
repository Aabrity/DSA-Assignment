package com.example.que1;
import java.util.PriorityQueue;

public class que1b {
    public static int minTimeToBuildEngines(int[] engines, int splitCost) {
        if (engines == null || engines.length == 0) {
            return 0;
        }

        // Initialize a priority queue (min heap) to keep track of engineers' workload
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        // Initialize the time taken for each engine
        int timeTaken = 0;

        // Initially, there's one engineer available
        int numEngineers = 1;

        // Loop through each engine
        for (int engine : engines) {
            // If there are available engineers, assign the engine to the engineer with the least workload
            if (numEngineers > 0) {
                // Get the engineer with the least workload
                int time = pq.isEmpty() ? 0 : pq.poll();

                // Update the time taken for this engine
                timeTaken = Math.max(timeTaken, time) + engine;

                // Add the updated time back to the priority queue
                pq.offer(timeTaken);

                // Decrease the number of available engineers
                numEngineers--;
            } else {
                // Split one engineer into two
                int splitTime = pq.poll();

                // Update the time taken for splitting
                timeTaken += splitCost;

                // Add the split engineers back to the priority queue
                pq.offer(splitTime + splitCost);
                pq.offer(timeTaken + engine);

                // Increase the number of available engineers
                numEngineers++;
            }
        }

        // The final time taken to build all engines is the maximum time in the priority queue
        return pq.peek();
    }

    public static void main(String[] args) {
        int[] engines = {1, 2, 3};
        int splitCost = 1;
        System.out.println(minTimeToBuildEngines(engines, splitCost)); // Output: 4
    }
}
