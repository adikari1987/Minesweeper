package com.gic.minesweeper.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;

    @BeforeEach
    void setUp() {
        cell = new Cell();
    }

    @Test
    void testNewCellHasNoMine() {
        assertFalse(cell.hasMine());
    }

    @Test
    void testNewCellIsNotRevealed() {
        assertFalse(cell.isRevealed());
    }

    @Test
    void testNewCellHasZeroAdjacentMines() {
        assertEquals(0, cell.getAdjacentMines());
    }

    @Test
    void testSetMine() {
        cell.setMine(true);
        assertTrue(cell.hasMine());
    }

    @Test
    void testReveal() {
        cell.reveal();
        assertTrue(cell.isRevealed());
    }

    @Test
    void testSetAdjacentMines() {
        cell.setAdjacentMines(3);
        assertEquals(3, cell.getAdjacentMines());
    }

    @Test
    void testToStringUnrevealed() {
        assertEquals("_", cell.toString());
    }

    @Test
    void testToStringRevealedWithMine() {
        cell.setMine(true);
        cell.reveal();
        assertEquals("*", cell.toString());
    }

    @Test
    void testToStringRevealedWithoutMine() {
        cell.setAdjacentMines(2);
        cell.reveal();
        assertEquals("2", cell.toString());
    }
}
