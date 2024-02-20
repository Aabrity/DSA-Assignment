package com.example.que3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class que3aTest {
    private que3a scoreTracker;

    @BeforeEach
    void setUp() {
        scoreTracker = new que3a();
    }

    @Test
    void getMedianScore_emptyScores_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> {
            scoreTracker.getMedianScore();
        });
    }

    @Test
    void getMedianScore_singleScore_returnsScore() {
        scoreTracker.addScore(85.5);
        assertEquals(85.5, scoreTracker.getMedianScore());
    }

    @Test
    void getMedianScore_evenNumberOfScores_returnsAverageOfMiddleTwo() {
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);

        assertEquals(87.8, scoreTracker.getMedianScore());
    }

    @Test
    void getMedianScore_oddNumberOfScores_returnsMiddleScore() {
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        scoreTracker.addScore(81.2);

        assertEquals(85.5, scoreTracker.getMedianScore());
    }
}
