package com.example.que1;

public class que1a {
    public static int minCostToDecorate(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        // Validate input matrix
        for (int[] row : costs) {
            for (int cost : row) {
                if (cost <= 0) {
                    throw new IllegalArgumentException("Costs must be positive integers.");
                }
            }
        }

        // Initialize dp array to store minimum costs
        int[][] dp = new int[n][k];

        // Initialize the first row with the costs of decorating the first venue
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        // Iterate through the venues and calculate the minimum cost
        for (int i = 1; i < n; i++) {
            // Iterate through the themes for the current venue
            for (int j = 0; j < k; j++) {
                // Initialize the minimum cost to decorate the current venue with the current theme
                int minCost = Integer.MAX_VALUE;
                // Iterate through the themes for the previous venue
                for (int prevTheme = 0; prevTheme < k; prevTheme++) {
                    // If the previous venue was decorated with a different theme,
                    // update the minimum cost accordingly
                    if (prevTheme != j) {
                        minCost = Math.min(minCost, dp[i - 1][prevTheme]);
                    }
                }
                // Update the dp array with the minimum cost for the current venue and theme
                dp[i][j] = minCost + costs[i][j];
            }
        }

        // Return the minimum cost to decorate all the venues
        int minResult = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            minResult = Math.min(minResult, dp[n - 1][j]);
        }
        return minResult;
    }

    public static void main(String[] args) {
        // Test the function with the provided example
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        System.out.println(minCostToDecorate(costs));  // Output: 7
    }
}
