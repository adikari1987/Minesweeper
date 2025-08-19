package com.gic.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid(3);
    }

    @Test
    void testGridCreation() {
        assertEquals(3, grid.getSize());
    }

    @Test
    void testInvalidGridSize() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(0));
        assertThrows(IllegalArgumentException.class, () -> new Grid(-1));
    }

    @Test
    void testGetCell() {
        Cell cell = grid.getCell(1, 1);
        assertNotNull(cell);
        assertFalse(cell.hasMine());
    }

    @Test
    void testPlaceMine() {
        grid.placeMine(1, 1);
        assertTrue(grid.getCell(1, 1).hasMine());
    }

    @Test
    void testInvalidPosition() {
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(0, -1));
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(3, 0));
        assertThrows(IllegalArgumentException.class, () -> grid.getCell(0, 3));
    }

    @Test
    void testGetAdjacentCells() {
        var adjacentCells = grid.getAdjacentCells(1, 1);
        assertEquals(8, adjacentCells.size());

        adjacentCells = grid.getAdjacentCells(0, 0);
        assertEquals(3, adjacentCells.size());
    }

    @Test
    void testCalculateAdjacentMines() {
        grid.placeMine(0, 0);
        grid.placeMine(0, 1);
        grid.calculateAdjacentMines();

        assertEquals(2, grid.getCell(1, 1).getAdjacentMines());
    }

    @Test
    void testRevealCell() {
        grid.revealCell(1, 1);
        assertTrue(grid.getCell(1, 1).isRevealed());
    }

    @Test
    void testAutoRevealWithZeroAdjacentMines() {
        grid.calculateAdjacentMines();
        grid.revealCell(2, 2);

        // Should auto-reveal all cells since no mines
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertTrue(grid.getCell(i, j).isRevealed());
            }
        }
    }

    @Test
    void testAreAllNonMineCellsRevealed() {
        grid.placeMine(0, 0);

        // Reveal all non-mine cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!grid.getCell(i, j).hasMine()) {
                    grid.revealCell(i, j);
                }
            }
        }

        assertTrue(grid.areAllNonMineCellsRevealed());
    }
}
