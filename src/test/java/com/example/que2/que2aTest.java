package com.example.que2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class que2aTest {

    @Test
    void testMinMovesToEqualizeDresses() {
        int[] sewingMachines1 = {1, 0, 5};
        int expectedMoves1 = 3;
        assertEquals(expectedMoves1, que2a.minMovesToEqualizeDresses(sewingMachines1));

        int[] sewingMachines2 = {2, 2, 2};
        int expectedMoves2 = 0;
        assertEquals(expectedMoves2, que2a.minMovesToEqualizeDresses(sewingMachines2));

        int[] sewingMachines3 = {3, 3, 3};
        int expectedMoves3 = 0;
        assertEquals(expectedMoves3, que2a.minMovesToEqualizeDresses(sewingMachines3));

        int[] sewingMachines4 = {1, 2, 3};
        int expectedMoves4 = 1;
        assertEquals(expectedMoves4, que2a.minMovesToEqualizeDresses(sewingMachines4));

        int[] sewingMachines5 = {0, 0, 0};
        int expectedMoves5 = 0;
        assertEquals(expectedMoves5, que2a.minMovesToEqualizeDresses(sewingMachines5));
    }
}
