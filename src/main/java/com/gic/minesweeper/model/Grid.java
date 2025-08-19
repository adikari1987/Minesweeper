package com.gic.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final int size;
    private final Cell[][] cells;

    public Grid(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than zero");
        }
        this.size = size;
        this.cells = new Cell[size][size];
        initialCells();
    }

    private void initialCells() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = new Cell();
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int row, int col) {
        validatePosition(row, col);
        return cells[row][col];
    }

    public void placeMine(int row, int col) {
        validatePosition(row, col);
        cells[row][col].setMine(true);
    }

    public List<Cell> getAdjacentCells(int row, int col) {
        validatePosition(row, col);
        List<Cell> adjacentCells = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i;
                int newCol = col + j;

                if (isValidPosition(newRow, newCol)) {
                    adjacentCells.add(cells[newRow][newCol]);
                }
            }
        }

        return adjacentCells;
    }

    public void calculateAdjacentMines() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (!cells[row][col].hasMine()) {
                    int mineCount = (int) getAdjacentCells(row, col)
                            .stream()
                            .filter(Cell::hasMine)
                            .count();
                    cells[row][col].setAdjacentMines(mineCount);
                }
            }
        }
    }

    public void revealCell(int row, int col) {
        validatePosition(row, col);
        Cell cell = cells[row][col];

        if (cell.isRevealed()) {
            return;
        }

        cell.reveal();

        if (cell.getAdjacentMines() == 0 && !cell.hasMine()) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;

                    int newRow = row + i;
                    int newCol = col + j;

                    if (isValidPosition(newRow, newCol)) {
                        revealCell(newRow, newCol);
                    }
                }
            }
        }
    }

    public boolean areAllNonMineCellsRevealed() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Cell cell = cells[row][col];
                if (!cell.hasMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void validatePosition(int row, int col) {
        if (!isValidPosition(row, col)) {
            throw new IllegalArgumentException("Invalid position: (" + row + ", " + col + ")");
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}
