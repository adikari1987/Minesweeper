package com.gic.minesweeper.view;

import com.gic.minesweeper.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserInterfaceTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream printStream;
    private UserInterfaceImpl ui;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(outputStream);
    }

    private UserInterfaceImpl createUIWithInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        return new UserInterfaceImpl(inputStream, printStream);
    }

    @Test
    void testGetValidGridSize() {
        ui = createUIWithInput("4\n");
        int result = ui.getGridSize();
        assertEquals(4, result);
        assertTrue(outputStream.toString().contains("size of the grid"));
    }

    @Test
    void testGetGridSizeWithInvalidThenValid() {
        ui = createUIWithInput("0\nabc\n4\n");
        int result = ui.getGridSize();
        assertEquals(4, result);
        assertTrue(outputStream.toString().contains("Invalid input"));
    }

    @Test
    void testGetValidMineCount() {
        ui = createUIWithInput("3\n");
        int result = ui.getMineCount(4);
        assertEquals(3, result);
        assertTrue(outputStream.toString().contains("number of mines"));
    }

    @Test
    void testGetMineCountWithInvalidThenValid() {
        ui = createUIWithInput("10\n-1\n3\n");
        int result = ui.getMineCount(4);
        assertEquals(3, result);
        assertTrue(outputStream.toString().contains("Invalid input"));
    }

    @Test
    void testGetValidPosition() {
        ui = createUIWithInput("A1\n");
        Position result = ui.getPosition(4);
        assertEquals(0, result.row);
        assertEquals(0, result.col);
        assertTrue(outputStream.toString().contains("square to reveal"));
    }

    @Test
    void testGetPositionWithInvalidThenValid() {
        ui = createUIWithInput("Z9\n1A\nA1\n");
        Position result = ui.getPosition(4);
        assertEquals(0, result.row);
        assertEquals(0, result.col);
        assertTrue(outputStream.toString().contains("Invalid input"));
    }

    @Test
    void testDisplayMessage() {
        ui = createUIWithInput("");
        ui.displayMessage("Test message");
        assertTrue(outputStream.toString().contains("Test message"));
    }

    @Test
    void testDisplayGrid() {
        ui = createUIWithInput("");
        ui.displayGrid("  1 2\nA _ _\nB _ _\n", true);
        String output = outputStream.toString();
        assertTrue(output.contains("Here is your minefield:"));
        assertTrue(output.contains("  1 2"));
    }

    @Test
    void testDisplayUpdatedGrid() {
        ui = createUIWithInput("");
        ui.displayGrid("  1 2\nA 1 _\nB _ _\n", false);
        String output = outputStream.toString();
        assertTrue(output.contains("Here is your updated minefield:"));
        assertTrue(output.contains("A 1 _"));
    }

    @Test
    void testWaitForPlayAgain() {
        ui = createUIWithInput("\n");
        ui.waitForPlayAgain();
        assertTrue(outputStream.toString().contains("Press any key to play again"));
    }

    @Test
    void testGetDisplay() {
        ui = createUIWithInput("");
        assertNotNull(ui.getDisplay());
        assertInstanceOf(GameConsole.class, ui.getDisplay());
    }

    @Test
    void testMineCountPromptShowsMaximum() {
        ui = createUIWithInput("2\n");
        ui.getMineCount(4);
        assertTrue(outputStream.toString().contains("maximum is 35%"));
    }
}
