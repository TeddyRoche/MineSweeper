package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    // Map to store the adjacency list representation of the graph
    private Map<String, List<String>> adjacencyList;
    // Static variables to store the number of rows and columns in the grid
    static int row = 0;
    static int col = 0;

    // Constructor to initialize the graph with a grid structure
    public Graph(int rows, int cols) {
        // Initialize the adjacency list
        this.adjacencyList = new HashMap<>();
        // Set the number of rows and columns
        row = rows;
        col = cols;
        // Initialize the grid with vertices and edges
        initializeGrid(rows, cols);
    }

    // Method to initialize the grid with vertices and edges
    private void initializeGrid(int rows, int cols) {
        // Iterate over each cell in the grid
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Generate a vertex label for the current cell
                String vertex = getVertexLabel(i, j);
                // Initialize a list to store the neighbors of the current vertex
                List<String> neighbors = new ArrayList<>();
                // Add adjacent vertices to the current vertex's neighbor list
                if (i > 0) {
                    neighbors.add(getVertexLabel(i - 1, j)); // Vertex to the top
                }
                if (i < rows - 1) {
                    neighbors.add(getVertexLabel(i + 1, j)); // Vertex to the bottom
                }
                if (j > 0) {
                    neighbors.add(getVertexLabel(i, j - 1)); // Vertex to the left
                }
                if (j < cols - 1) {
                    neighbors.add(getVertexLabel(i, j + 1)); // Vertex to the right
                }
                // Add the vertex and its list of neighbors to the adjacency list
                adjacencyList.put(vertex, neighbors);
            }
        }
    }

    // Method to generate a vertex label based on row and column indices
    private String getVertexLabel(int row, int col) {
        return "(" + row + "," + col + ")";
    }

    // Method to get the list of adjacent vertices for a given vertex
    public List<String> getAdjacentVertices(String vertex) {
        return adjacencyList.get(vertex);
    }

    // Method to get all vertices in the graph
    public Set<String> getAllVertices() {
        return adjacencyList.keySet();
    }

    // Method to provide a string representation of the graph
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Iterate over each vertex and its list of neighbors
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            // Append the vertex label
            sb.append(entry.getKey()).append(" -> ");
            // Append the list of neighbors
            sb.append(entry.getValue()).append("\n");
        }
        // Return the string representation of the graph
        return sb.toString();
    }

    public void printAdjacencyList() {
        System.out.println("Adjacency List:");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            List<String> neighbors = entry.getValue();
            for (String neighbor : neighbors) {
                System.out.print(neighbor + ", ");
            }
            System.out.println();
        }
    }

}
