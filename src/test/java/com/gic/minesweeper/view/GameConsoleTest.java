package com.gic.minesweeper.view;

import com.gic.minesweeper.model.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameConsoleTest {

    private GameConsole display;
    private Grid grid;

    @BeforeEach
    void setUp() {
        display = new GameConsole();
        grid = new Grid(3);
    }

    @Test
    void testFormatEmptyGrid() {
        String result = display.formatGrid(grid);

        assertTrue(result.contains("  1 2 3"));
        assertTrue(result.contains("A _ _ _"));
        assertTrue(result.contains("B _ _ _"));
        assertTrue(result.contains("C _ _ _"));
    }

    @Test
    void testFormatGridWithRevealedCells() {
        grid.getCell(0, 0).reveal();
        grid.getCell(0, 0).setAdjacentMines(2);
        grid.getCell(1, 1).reveal();
        grid.getCell(1, 1).setMine(true);

        String result = display.formatGrid(grid);

        assertTrue(result.contains("A 2 _ _"));
        assertTrue(result.contains("B _ * _"));
    }

    @Test
    void testGetWelcomeMessage() {
        assertEquals("Welcome to Minesweeper!\n", display.getWelcomeMessage());
    }

    @Test
    void testGetGridSizePrompt() {
        String prompt = display.getGridSizePrompt();
        assertTrue(prompt.contains("size of the grid"));
        assertTrue(prompt.contains("4x4"));
    }

    @Test
    void testGetMineCountPrompt() {
        String prompt = display.getMineCountPrompt();
        assertTrue(prompt.contains("number of mines"));
    }

    @Test
    void testGetPositionPrompt() {
        String prompt = display.getPositionPrompt();
        assertTrue(prompt.contains("square to reveal"));
        assertTrue(prompt.contains("A1"));
    }

    @Test
    void testGetAdjacentMinesMessage() {
        assertEquals("This square contains 3 adjacent mines.",
                display.getAdjacentMinesMessage(3));
        assertEquals("This square contains 0 adjacent mines.",
                display.getAdjacentMinesMessage(0));
    }

    @Test
    void testGetGameOverMessage() {
        String message = display.getGameOverMessage();
        assertTrue(message.contains("detonated a mine"));
        assertTrue(message.contains("Game over"));
    }

    @Test
    void testGetWinMessage() {
        String message = display.getWinMessage();
        assertTrue(message.contains("Congratulations"));
        assertTrue(message.contains("won"));
    }

    @Test
    void testGetPlayAgainPrompt() {
        String prompt = display.getPlayAgainPrompt();
        assertTrue(prompt.contains("Press any key"));
        assertTrue(prompt.contains("play again"));
    }

    @Test
    void testGetGridHeader() {
        assertEquals("\nHere is your minefield:", display.getHeader());
    }

    @Test
    void testGetUpdatedGridHeader() {
        assertEquals("\nHere is your updated minefield:", display.getUpdatedHeader());
    }

    @Test
    void testFormatLargerGrid() {
        Grid largeGrid = new Grid(5);
        String result = display.formatGrid(largeGrid);

        assertTrue(result.contains("  1 2 3 4 5"));
        assertTrue(result.contains("E _ _ _ _ _"));
    }
}
