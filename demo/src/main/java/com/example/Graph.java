package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private Map<String, List<String>> adjacencyList;
    static int row = 0;
    static int col = 0;

    public Graph(int rows, int cols) {
        this.adjacencyList = new HashMap<>();
        row = rows;
        col = cols;
        initializeGrid(rows, cols);
    }

    private void initializeGrid(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String vertex = getVertexLabel(i, j);
                List<String> neighbors = new ArrayList<>();
                if (i > 0) {
                    neighbors.add(getVertexLabel(i - 1, j)); 
                }
                if (i < rows - 1) {
                    neighbors.add(getVertexLabel(i + 1, j)); 
                }
                if (j > 0) {
                    neighbors.add(getVertexLabel(i, j - 1)); 
                }
                if (j < cols - 1) {
                    neighbors.add(getVertexLabel(i, j + 1)); 
                }
                adjacencyList.put(vertex, neighbors);
            }
        }
    }

    private String getVertexLabel(int row, int col) {
        return "(" + row + "," + col + ")";
    }

    public List<String> getAdjacentVertices(String vertex) {
        return adjacencyList.get(vertex);
    }

    public Set<String> getAllVertices() {
        return adjacencyList.keySet();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(" -> ");
            sb.append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
