package com.example.que4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class que4a {
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int minMovesToCollectAllKeys(char[][] grid) {
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; 
        int m = grid.length; 
        int n = grid[0].length; 
        Set<String> visited = new HashSet<>(); 
        Point start = null; 
        int numKeys = 0; 

        // Iterate through the grid to find the start point and count the number of keys
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'S') {
                    start = new Point(i, j); // Found the start point
                } else if (grid[i][j] >= 'a' && grid[i][j] <= 'z') {
                    numKeys++; // Increment the count of keys
                }
            }
        }

        Deque<Object[]> queue = new ArrayDeque<>(); // Queue for BFS traversal
        queue.offerLast(new Object[]{start, new HashSet<>(), 0}); // Add start point to the queue

        while (!queue.isEmpty()) {
            Object[] current = queue.pollFirst(); // Dequeue current point
            Point currentPoint = (Point) current[0]; // Extract current point
            Set<Character> keysCollected = (Set<Character>) current[1]; // Extract keys collected so far
            int moves = (int) current[2]; 

            if (keysCollected.size() == numKeys) { // If all keys are collected
                return moves; 
            }

            for (int[] dir : directions) { 
                int nx = currentPoint.x + dir[0]; // Calculate next x-coordinate
                int ny = currentPoint.y + dir[1]; // Calculate next y-coordinate

                if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx][ny] != 'W' &&
                        !visited.contains(nx + "," + ny + "," + keysCollected)) {
                    visited.add(nx + "," + ny + "," + keysCollected); // Mark the point as visited

                    if (grid[nx][ny] >= 'a' && grid[nx][ny] <= 'z') {
                        Set<Character> newKeysCollected = new HashSet<>(keysCollected);
                        newKeysCollected.add(grid[nx][ny]); // Collect the key
                        queue.offerLast(new Object[]{new Point(nx, ny), newKeysCollected, moves + 1}); // Enqueue the next point
                    } else if (grid[nx][ny] >= 'A' && grid[nx][ny] <= 'Z' &&
                            keysCollected.contains(Character.toLowerCase(grid[nx][ny]))) {
                        queue.offerLast(new Object[]{new Point(nx, ny), keysCollected, moves + 1}); 
                    } else {
                        queue.offerLast(new Object[]{new Point(nx, ny), keysCollected, moves + 1}); 
                    }
                }
            }
        }

        return -1; // If all keys cannot be collected
    }

    public static void main(String[] args) {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'}, 
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };
        System.out.println(minMovesToCollectAllKeys(grid)); // Output: 8
    }
}
