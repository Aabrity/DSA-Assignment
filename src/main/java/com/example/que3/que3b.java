package com.example.que3;
import java.util.*;
// Class to represent a graph edge
class Edge {
    int src, dest, weight;

    // Constructor
    Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
};

class UnionFind {
    int parent, rank;

    // Constructor
    UnionFind(int parent, int rank) {
        this.parent = parent;
        this.rank = rank;
    }
}

class Graph {
    int V, E;
    List<Edge> edges;

    // Constructor
    Graph(int v, int e) {
        V = v;
        E = e;
        edges = new ArrayList<>();
    }

    // A utility function to find set of an element i
    int find(UnionFind subsets[], int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
        return subsets[i].parent;
    }

    // A function that does union of two sets of x and y
    void Union(UnionFind subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root of high rank tree
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

            // If ranks are the same, then make one as root and increment its rank by one
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    // Kruskal's algorithm
    void KruskalMST() {
        List<Edge> result = new ArrayList<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);

        // Add all edges to the priority queue
        for (Edge edge : edges) {
            pq.add(edge);
        }

        UnionFind[] subsets = new UnionFind[V];
        for (int i = 0; i < V; ++i)
            subsets[i] = new UnionFind(i, 0);

        // Number of edges to be taken is equal to V-1
        while (result.size() < V - 1) {
            // Step 2: Pick the smallest edge. And increment the index for next iteration
            Edge next_edge = pq.poll();

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // If including this edge doesn't cause a cycle, include it in result
            if (x != y) {
                result.add(next_edge);
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // Print the contents of result[] to display the built MST
        System.out.println("Following are the edges in the constructed MST");
        for (Edge edge : result) {
            System.out.println(edge.src + " -- " + edge.dest + " == " + edge.weight);
        }
    }
}

public class que3b {
    public static void main(String[] args) {
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

        // Function call
        graph.KruskalMST();
    }
}


