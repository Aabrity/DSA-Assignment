package com.example.que1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class que1bTest {

    @Test
    void minTimeToBuildEngines() {
        // Test case 1: Engines array is null
        assertEquals(0, que1b.minTimeToBuildEngines(null, 1));

        // Test case 2: Engines array is empty
        assertEquals(0, que1b.minTimeToBuildEngines(new int[]{}, 1));

        // Test case 3: Regular test case
        int[] engines = {1, 2, 3};
        int splitCost = 1;
        assertEquals(4, que1b.minTimeToBuildEngines(engines, splitCost));

        // Additional test cases can be added as needed
    }
}
