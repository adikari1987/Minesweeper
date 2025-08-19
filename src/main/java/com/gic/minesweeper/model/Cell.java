package com.gic.minesweeper.model;

public class Cell {

    private boolean hasMine;
    private boolean isRevealed;
    private int adjacentMines;

    public Cell() {
        this.hasMine = false;
        this.isRevealed = false;
        this.adjacentMines = 0;
    }

    public boolean hasMine() {
        return hasMine;
    }

    public void setMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        this.isRevealed = true;
    }

    public int getAdjacentMines() {
        return adjacentMines;
    }

    public void setAdjacentMines(int adjacentMines) {
        this.adjacentMines = adjacentMines;
    }

    @Override
    public String toString() {
        if (!isRevealed) {
            return "_";
        }
        return hasMine ? "*" : String.valueOf(adjacentMines);
    }
}
