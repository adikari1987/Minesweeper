package com.gic.minesweeper.service;

import com.gic.minesweeper.model.Position;

public interface InputValidator {

    boolean isValidGridSize(String input);

    boolean isValidMineCount(String input, int gridSize);

    boolean isValidPosition(String input, int gridSize);

    Position parsePosition(String input, int gridSize);

}
