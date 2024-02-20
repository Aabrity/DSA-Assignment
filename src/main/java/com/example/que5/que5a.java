package com.example.que5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


 // This class represents an Ant Colony Optimization algorithm implementation for solving the Traveling Salesman Problem (TSP).
 
public class que5a {
    private double[][] distances; // Matrix of distances between cities
    private double[][] pheromones; // Matrix of pheromone levels between cities
    private int numAnts; 
    private double alpha; 
    private double beta; 
    private double evaporationRate; 
    private Random random; 

    //Constructor to initialize the Ant Colony Optimization algorithm with given parameters.
     
   
    public que5a(double[][] distances, int numAnts, double alpha, double beta, double evaporationRate) {
        this.distances = distances;
        this.numAnts = numAnts;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.random = new Random();
        this.pheromones = new double[distances.length][distances.length];
        initializePheromones();
    }

    
     // Initializes pheromone levels to a default value.
     
    private void initializePheromones() {
        for (int i = 0; i < pheromones.length; i++) {
            Arrays.fill(pheromones[i], 1.0);
        }
    }

    
      //Finds the best path (shortest distance) through the TSP using Ant Colony Optimization.
     
   
    public List<Integer> findBestPath(int numIterations) {
        List<Integer> bestPath = null;
        double shortestDistance = Double.MAX_VALUE;

        for (int iteration = 0; iteration < numIterations; iteration++) {
            List<Integer> antPath = constructAntPath();
            double distance = calculatePathDistance(antPath);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                bestPath = new ArrayList<>(antPath);
            }

            updatePheromones(antPath, distance);
            evaporatePheromones();
        }

        return bestPath;
    }

    
     //Constructs a path for a single ant to traverse all cities.
    
    private List<Integer> constructAntPath() {
        List<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[distances.length];
        int start = random.nextInt(distances.length);
        path.add(start);
        visited[start] = true;

        while (path.size() < distances.length) {
            int currentCity = path.get(path.size() - 1);
            int nextCity = selectNextCity(currentCity, visited);
            path.add(nextCity);
            visited[nextCity] = true;
        }

        return path;
    }

    
     // Selects the next city for an ant to move to based on pheromone levels and distances.
   
    private int selectNextCity(int currentCity, boolean[] visited) {
        double totalProbability = 0.0;
        double[] probabilities = new double[distances.length];

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i]) {
                double pheromone = Math.pow(pheromones[currentCity][i], alpha);
                double attractiveness = Math.pow(1.0 / distances[currentCity][i], beta);
                probabilities[i] = pheromone * attractiveness;
                totalProbability += probabilities[i];
            }
        }

        double rand = random.nextDouble() * totalProbability;
        double cumulativeProbability = 0.0;

        for (int i = 0; i < distances.length; i++) {
            if (!visited[i]) {
                cumulativeProbability += probabilities[i];
                if (cumulativeProbability >= rand) {
                    return i;
                }
            }
        }

        throw new RuntimeException("No city was selected.");
    }

    
     // Calculates the total distance of a given path.
    
    private double calculatePathDistance(List<Integer> path) {
        double distance = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            distance += distances[path.get(i)][path.get(i + 1)];
        }

        distance += distances[path.get(path.size() - 1)][path.get(0)];
        return distance;
    }

    
     // Updates pheromone levels based on the path chosen by an ant.
   
    private void updatePheromones(List<Integer> path, double distance) {
        double pheromoneDelta = 1.0 / distance;

        for (int i = 0; i < path.size() - 1; i++) {
            int city1 = path.get(i);
            int city2 = path.get(i + 1);
            pheromones[city1][city2] += pheromoneDelta;
            pheromones[city2][city1] += pheromoneDelta;
        }
    }

    
     // Evaporates pheromone levels according to the evaporation rate.
     
    private void evaporatePheromones() {
        for (int i = 0; i < pheromones.length; i++) {
            for (int j = 0; j < pheromones[i].length; j++) {
                pheromones[i][j] *= (1.0 - evaporationRate);
            }
        }
    }

    
     //Main method to demonstrate the usage of the Ant Colony Optimization algorithm.
    
    public static void main(String[] args) {
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

        System.out.println("Best path: " + bestPath);
    }
}
