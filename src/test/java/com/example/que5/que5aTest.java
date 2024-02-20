package com.example.que5;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class que5aTest {




    @Test
    void findBestPath_ValidDistances_ReturnsBestPath() {
        double[][] distances = {
                {0, 1, 2, 3},
                {1, 0, 4, 5},
                {2, 4, 0, 6},
                {3, 5, 6, 0}
        };
        int numAnts = 5;
        double alpha = 1;
        double beta = 2;
        double evaporationRate = 0.1;
        int numIterations = 100;

        que5a antColony = new que5a(distances, numAnts, alpha, beta, evaporationRate);
        List<Integer> bestPath = antColony.findBestPath(numIterations);

        assertNotNull(bestPath);
        assertFalse(bestPath.isEmpty());
    }
}
