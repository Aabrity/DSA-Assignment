package com.example.que3;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class que3bTest {


    @Test
    void KruskalMST() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int V = 4; // Number of vertices in graph
        int E = 5; // Number of edges in graph
        Graph graph = new Graph(V, E);

        // add edge 0-1
        graph.edges.add(new Edge(0, 1, 10));

        // add edge 0-2
        graph.edges.add(new Edge(0, 2, 6));

        // add edge 0-3
        graph.edges.add(new Edge(0, 3, 5));

        // add edge 1-3
        graph.edges.add(new Edge(1, 3, 15));

        // add edge 2-3
        graph.edges.add(new Edge(2, 3, 4));

        // Capture the output
        graph.KruskalMST();
        String actualOutput = outContent.toString();
        String expectedOutput = "Following are the edges in the constructed MST\n" +
                "2 -- 3 == 4\n" +
                "0 -- 3 == 5\n" +
                "0 -- 1 == 10\n";

        // Split expected and actual outputs into lines
        String[] expectedLines = expectedOutput.split("\\R");
        String[] actualLines = actualOutput.split("\\R");

        // Compare each line individually
        for (int i = 0; i < expectedLines.length; i++) {
            assertEquals(expectedLines[i], actualLines[i],
                    "Mismatch in line " + (i + 1) + ":\nExpected: " + expectedLines[i] + "\nActual: " + actualLines[i]);
        }
    }}

