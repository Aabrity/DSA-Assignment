package com.example.que4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class que4aTest {

    @Test
    void minMovesToCollectAllKeys_SampleGrid_ReturnsCorrectResult() {
        char[][] grid = {
                {'S', 'P', 'q', 'P', 'P'},
                {'W', 'W', 'W', 'P', 'W'},
                {'r', 'P', 'Q', 'P', 'R'}
        };

        int expectedMoves = 8;
        int actualMoves = que4a.minMovesToCollectAllKeys(grid);

        assertEquals(expectedMoves, actualMoves);
    }
}
