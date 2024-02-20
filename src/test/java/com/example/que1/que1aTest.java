package com.example.que1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class que1aTest {

    @Test
    void minCostToDecorate() {
        // Test case with the provided example
        int[][] costs = {{1, 3, 2}, {4, 6, 8}, {3, 1, 5}};
        int expected = 7; // The expected minimum cost

        int actual = que1a.minCostToDecorate(costs);

        assertEquals(expected, actual, "The minimum cost should be 7.");
    }

    @Test
    void minCostToDecorate_EmptyInput() {
        // Test case with empty input
        int[][] costs = {};
        int expected = 0; // The expected minimum cost is 0 for empty input

        int actual = que1a.minCostToDecorate(costs);

        assertEquals(expected, actual, "The minimum cost should be 0 for empty input.");
    }

    @Test
    void minCostToDecorate_NullInput() {
        // Test case with null input
        int[][] costs = null;
        int expected = 0; // The expected minimum cost is 0 for null input

        int actual = que1a.minCostToDecorate(costs);

        assertEquals(expected, actual, "The minimum cost should be 0 for null input.");
    }

    @Test
    void minCostToDecorate_NegativeCost() {
        // Test case with negative cost
        int[][] costs = {{1, -3, 2}, {4, 6, 8}, {3, 1, 5}};

        assertThrows(IllegalArgumentException.class, () -> {
            que1a.minCostToDecorate(costs);
        }, "An IllegalArgumentException should be thrown for negative cost.");
    }
}
