package com.example.que5;

//package com.example.que5;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class que5bTest {

    @Test
    public void testFindNodesWithOnlyTargetAsParent() {
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 6}, {2, 4}, {4, 6}, {4, 5}, {5, 7}};
        int target = 4;

        List<Integer> expected = Arrays.asList( 5, 7);
        List<Integer> result = que5b.findNodesWithOnlyTargetAsParent(edges, target);

        assertEquals("Size of result list should match", expected.size(), result.size());
        assertTrue("Result list should contain all expected elements", result.containsAll(expected));

    }
}
