package com.gic.minesweeper.service.impl;

import com.gic.minesweeper.model.Cell;
import com.gic.minesweeper.model.GameStates;
import com.gic.minesweeper.model.Grid;
import com.gic.minesweeper.service.MinesweeperEngine;
import com.gic.minesweeper.service.RandomMineGenerator;

public class MinesweeperEngineImpl implements MinesweeperEngine {

    private Grid grid;
    private GameStates gameStates;
    private final RandomMineGenerator randomMineGenerator;

    public MinesweeperEngineImpl() {
        this.randomMineGenerator = new RandomMineGeneratorImpl();
        this.gameStates = GameStates.PLAY;
    }

    public MinesweeperEngineImpl(RandomMineGenerator randomMineGenerator) {
        this.randomMineGenerator = randomMineGenerator;
        this.gameStates = GameStates.PLAY;
    }

    public void initializeGame(int gridSize, int mineCount) {
        this.grid = new Grid(gridSize);
        this.gameStates = GameStates.PLAY;
        randomMineGenerator.placeMines(grid, mineCount);
    }

    // manage game states with row, column values
    public GameStates makeMove(int row, int col) {
        if (gameStates != GameStates.PLAY) {
            return gameStates;
        }

        Cell cell = grid.getCell(row, col);

        if (cell.isRevealed()) {
            return gameStates;
        }

        if (cell.hasMine()) {
            grid.revealCell(row, col);
            gameStates = GameStates.LOST;
        } else {
            grid.revealCell(row, col);
            if (grid.areAllNonMineCellsRevealed()) {
                gameStates = GameStates.WON;
            }
        }

        return gameStates;
    }

    public Grid getGrid() {
        return grid;
    }

    public GameStates getGameState() {
        return gameStates;
    }

    public int getAdjacentMineCount(int row, int col) {
        return grid.getCell(row, col).getAdjacentMines();
    }

}
