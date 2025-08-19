package com.gic.minesweeper.service;

import com.gic.minesweeper.model.Position;
import com.gic.minesweeper.service.impl.InputValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidatorTest {

    private InputValidatorImpl validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidatorImpl();
    }

    @Test
    void testValidGridSize() {
        assertTrue(validator.isValidGridSize("4"));
        assertTrue(validator.isValidGridSize("10"));
        assertTrue(validator.isValidGridSize("1"));
    }

    @Test
    void testInvalidGridSize() {
        assertFalse(validator.isValidGridSize("0"));
        assertFalse(validator.isValidGridSize("-1"));
        assertFalse(validator.isValidGridSize("abc"));
        assertFalse(validator.isValidGridSize(""));
        assertFalse(validator.isValidGridSize("27")); // Too large
    }

    @Test
    void testValidMineCount() {
        assertTrue(validator.isValidMineCount("3", 4)); // 3 mines in 4x4 grid
        assertTrue(validator.isValidMineCount("0", 4)); // No mines
        assertTrue(validator.isValidMineCount("5", 4)); // 5 mines (35% of 16 = 5.6, so 5 is valid)
    }

    @Test
    void testInvalidMineCount() {
        assertFalse(validator.isValidMineCount("10", 4)); // Too many mines
        assertFalse(validator.isValidMineCount("-1", 4)); // Negative mines
        assertFalse(validator.isValidMineCount("abc", 4)); // Non-numeric
        assertFalse(validator.isValidMineCount("", 4)); // Empty string
    }

    @Test
    void testValidPosition() {
        assertTrue(validator.isValidPosition("A1", 4));
        assertTrue(validator.isValidPosition("D4", 4));
        assertTrue(validator.isValidPosition("a1", 4)); // Should work with lowercase
        assertTrue(validator.isValidPosition("B2", 4));
    }

    @Test
    void testInvalidPosition() {
        assertFalse(validator.isValidPosition("A0", 4)); // Invalid column
        assertFalse(validator.isValidPosition("A5", 4)); // Column out of range
        assertFalse(validator.isValidPosition("E1", 4)); // Row out of range
        assertFalse(validator.isValidPosition("1A", 4)); // Wrong format
        assertFalse(validator.isValidPosition("AA", 4)); // Wrong format
        assertFalse(validator.isValidPosition("11", 4)); // Wrong format
        assertFalse(validator.isValidPosition("", 4)); // Empty string
        assertFalse(validator.isValidPosition("A", 4)); // Incomplete
    }

    @Test
    void testParsePosition() {
        Position position = validator.parsePosition("A1", 4);
        assertEquals(0, position.row);
        assertEquals(0, position.col);

        position = validator.parsePosition("B3", 4);
        assertEquals(1, position.row);
        assertEquals(2, position.col);

        position = validator.parsePosition("d4", 4);
        assertEquals(3, position.row);
        assertEquals(3, position.col);
    }

    @Test
    void testParseInvalidPosition() {
        assertEquals(null, validator.parsePosition("A0", 4));
        assertThrows(Exception.class, () -> {
            validator.parsePosition("1A", 4);
        });
    }

    @Test
    void testPosition() {
        Position position = new Position(2, 3);
        assertEquals(2, position.row);
        assertEquals(3, position.col);
    }
}
