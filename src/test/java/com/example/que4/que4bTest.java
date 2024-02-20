package com.example.que4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class que4bTest {

    @Test
    void closestKValues_SampleInput_ReturnsCorrectResult() {
        // Construct the balanced binary search tree
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        // Define the target value and the number of closest values
        double target = 3.8;
        int numClosest = 2;

        que4b solution = new que4b();
        List<Integer> result = solution.closestKValues(root, target, numClosest);

        // Check if the result matches the expected values
        assertEquals(2, result.size());
        assertEquals(3, result.get(0));
        assertEquals(4, result.get(1));
    }
}
