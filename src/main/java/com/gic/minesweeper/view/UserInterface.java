package com.gic.minesweeper.view;

import com.gic.minesweeper.model.Position;

public interface UserInterface {
    int getGridSize();
    int getMineCount(int gridSize);
    Position getPosition(int gridSize);
    void displayMessage(String message);
    void displayGrid(String gridString, boolean isInitial);
    void waitForPlayAgain();
    GameConsole getDisplay();
}
