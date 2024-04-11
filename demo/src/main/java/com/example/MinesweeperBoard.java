package com.example;

import java.util.Random;

public class MinesweeperBoard {

    private int[][] board;
    private boolean[][] revealed;
    private int numRows;
    private int numCols;
    private int numMines;

    public MinesweeperBoard(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.board = new int[numRows][numCols];
        this.revealed = new boolean[numRows][numCols];
        initializeBoard();
        placeMines();
        updateAdjacentCounts();
    }

    private void initializeBoard() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                board[i][j] = -1; 
            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numCols);
            if (board[row][col] != 9) { 
                board[row][col] = 9;
                minesPlaced++;
            }
        }
    }

    private void updateAdjacentCounts() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (board[i][j] != 9) { 
                    int count = 0;
                    for (int r = i - 1; r <= i + 1; r++) {
                        for (int c = j - 1; c <= j + 1; c++) {
                            if (r >= 0 && r < numRows && c >= 0 && c < numCols && board[r][c] == 9) {
                                count++;
                            }
                        }
                    }
                    board[i][j] = count; 
                }
            }
        }
    }

    public boolean isMineAt(int row, int col) {
        return board[row][col] == 9;
    }

    public void revealCell(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols || revealed[row][col]) {
            return; 
        }

        revealed[row][col] = true;

        if (board[row][col] == 0) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr != 0 || dc != 0) {
                        revealCell(row + dr, col + dc);
                    }
                }
            }
        }
    }
}



