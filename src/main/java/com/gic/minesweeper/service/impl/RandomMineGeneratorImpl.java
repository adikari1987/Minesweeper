package com.gic.minesweeper.service.impl;

import com.gic.minesweeper.model.Grid;
import com.gic.minesweeper.service.RandomMineGenerator;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomMineGeneratorImpl implements RandomMineGenerator {

    private final Random random;

    public RandomMineGeneratorImpl() {
        this.random = new Random();
    }

    public RandomMineGeneratorImpl(Random random) {
        this.random = random;
    }

    // generate the random mine position
    public void placeMines(Grid grid, int mineCount) {
        if (mineCount < 0) {
            throw new IllegalArgumentException("Mine count cannot be negative");
        }

        int maxMines = (int) (grid.getSize() * grid.getSize() * 0.35);
        if (mineCount > maxMines) {
            throw new IllegalArgumentException("Too many mines for grid size");
        }

        Set<String> minePositions = new HashSet<>();
        int size = grid.getSize();

        while (minePositions.size() < mineCount) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);
            String position = row + "," + col;

            if (minePositions.add(position)) {
                grid.placeMine(row, col);
            }
        }

        grid.calculateAdjacentMines();
    }
}
