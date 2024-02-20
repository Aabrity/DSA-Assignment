package com.example.que2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class que2bTest {

    @Test
    void findSecrets() {
        int n = 5;
        int[][] intervals = {{0, 2}, {1, 3}, {2, 4}};
        int firstPerson = 0;

        List<Integer> result = que2b.findSecrets(n, intervals, firstPerson);

        assertEquals(5, result.size()); // Expecting all numbers from 0 to 4 to be covered
        assertEquals(List.of(0, 1, 2, 3, 4), result); // Expecting the list [0, 1, 2, 3, 4]
    }

    @Test
    void main() {
        // Since main method is void and prints to console, we cannot test its output directly.
        // We can only ensure that it runs without throwing an exception.
        que2b.main(new String[]{});
    }
}