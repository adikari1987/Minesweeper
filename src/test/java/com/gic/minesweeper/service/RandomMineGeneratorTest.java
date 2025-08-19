package com.gic.minesweeper.service;

import com.gic.minesweeper.model.Grid;
import com.gic.minesweeper.service.impl.RandomMineGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomMineGeneratorTest {

    private RandomMineGeneratorImpl mineGenerator;
    private Grid grid;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        mineGenerator = new RandomMineGeneratorImpl(random);
        grid = new Grid(4);
    }

    @Test
    void testPlaceMines() {
        mineGenerator.placeMines(grid, 3);

        int mineCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getCell(i, j).hasMine()) {
                    mineCount++;
                }
            }
        }

        assertEquals(3, mineCount);
    }

    @Test
    void testPlaceZeroMines() {
        mineGenerator.placeMines(grid, 0);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertFalse(grid.getCell(i, j).hasMine());
            }
        }
    }

    @Test
    void testTooManyMines() {
        assertThrows(IllegalArgumentException.class, () -> {
            mineGenerator.placeMines(grid, 10); // More than 35% of 16
        });
    }

    @Test
    void testNegativeMineCount() {
        assertThrows(IllegalArgumentException.class, () -> {
            mineGenerator.placeMines(grid, -1);
        });
    }

    @Test
    void testMaximumMines() {
        int maxMines = (int) (4 * 4 * 0.35); // Should be 5
        assertDoesNotThrow(() -> {
            mineGenerator.placeMines(grid, maxMines);
        });
    }

    @Test
    void testMineGeneratorWithFixedRandom() {
        Random fixedRandom = new Random(12345); // Fixed seed for reproducible tests
        RandomMineGenerator fixedGenerator = new RandomMineGeneratorImpl(fixedRandom);

        fixedGenerator.placeMines(grid, 2);

        int mineCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getCell(i, j).hasMine()) {
                    mineCount++;
                }
            }
        }

        assertEquals(2, mineCount);
    }

    @Test
    void testAdjacentMinesCalculated() {
        // Place a mine at (1,1) and verify adjacent cells have correct counts
        Grid testGrid = new Grid(3);
        Random fixedRandom = new Random(42);
        RandomMineGenerator testGenerator = new RandomMineGeneratorImpl(fixedRandom);

        // Manually place one mine for predictable testing
        testGrid.placeMine(1, 1);
        testGrid.calculateAdjacentMines();

        // Check that adjacent cells have count of 1
        assertEquals(1, testGrid.getCell(0, 0).getAdjacentMines());
        assertEquals(1, testGrid.getCell(0, 1).getAdjacentMines());
        assertEquals(1, testGrid.getCell(0, 2).getAdjacentMines());
        assertEquals(1, testGrid.getCell(1, 0).getAdjacentMines());
        assertEquals(1, testGrid.getCell(1, 2).getAdjacentMines());
        assertEquals(1, testGrid.getCell(2, 0).getAdjacentMines());
        assertEquals(1, testGrid.getCell(2, 1).getAdjacentMines());
        assertEquals(1, testGrid.getCell(2, 2).getAdjacentMines());
    }

}
