package com.gic.minesweeper.service.impl;

import com.gic.minesweeper.model.Position;
import com.gic.minesweeper.service.InputValidator;

import java.util.regex.Pattern;

public class InputValidatorImpl implements InputValidator {

    private static final Pattern POSITION_PATTERN = Pattern.compile("^[A-Z]\\d+$");

    // check the grid size between 0 and 26
    public boolean isValidGridSize(String input) {
        try {
            int size = Integer.parseInt(input);
            return size > 0 && size <= 26; // Limit to A-Z rows
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // check the maximum mine count fulfill 35%
    public boolean isValidMineCount(String input, int gridSize) {
        try {
            int mineCount = Integer.parseInt(input);
            int maxMines = (int) (gridSize * gridSize * 0.35);
            return mineCount >= 0 && mineCount <= maxMines;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // check the validity of cell position for row and column
    public boolean isValidPosition(String input, int gridSize) {
        if (!POSITION_PATTERN.matcher(input.toUpperCase()).matches()) {
            return false;
        }

        try {
            Position position = parsePosition(input, gridSize);
            return position.row >= 0 && position.row < gridSize &&
                    position.col >= 0 && position.col < gridSize;
        } catch (Exception e) {
            return false;
        }
    }

    // validate input for cell position
    public Position parsePosition(String input, int gridSize) {

        String upperInput = input.toUpperCase();
        char rowChar = upperInput.charAt(0);
        String colStr = upperInput.substring(1);

        int row = rowChar - 'A';
        int col = Integer.parseInt(colStr) - 1;

        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            return null;
        }

        return new Position(row, col);
    }

}
