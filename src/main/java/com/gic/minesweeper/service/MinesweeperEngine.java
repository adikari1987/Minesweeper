package com.gic.minesweeper.service;

import com.gic.minesweeper.model.GameStates;
import com.gic.minesweeper.model.Grid;

public interface MinesweeperEngine {
    void initializeGame(int gridSize, int mineCount);

    GameStates makeMove(int row, int col);

    Grid getGrid();

    GameStates getGameState();

    int getAdjacentMineCount(int row, int col);
}
