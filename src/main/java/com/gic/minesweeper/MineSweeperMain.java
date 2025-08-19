package com.gic.minesweeper;

import com.gic.minesweeper.model.Position;
import com.gic.minesweeper.model.GameStates;
import com.gic.minesweeper.service.MinesweeperEngine;
import com.gic.minesweeper.service.impl.MinesweeperEngineImpl;
import com.gic.minesweeper.view.UserInterface;
import com.gic.minesweeper.view.UserInterfaceImpl;

public class MineSweeperMain {

    private final MinesweeperEngine mineSweeperEngine;
    private final UserInterface userInterface;

    public MineSweeperMain(MinesweeperEngine mineSweeperEngine, UserInterface userInterface) {
        this.mineSweeperEngine = mineSweeperEngine;
        this.userInterface = userInterface;
    }

    public static void main(String[] args) {
        MinesweeperEngine minesweeperEngine = new MinesweeperEngineImpl();
        UserInterface userInterface = new UserInterfaceImpl(System.in, System.out);
        new MineSweeperMain(minesweeperEngine, userInterface).run();
    }

    public void run() {
        do {
            startGame();
            userInterface.waitForPlayAgain();
        } while (true);
    }

    private void startGame() {
        userInterface.displayMessage(userInterface.getDisplay().getWelcomeMessage());

        int gridSize = userInterface.getGridSize();
        int mineCount = userInterface.getMineCount(gridSize);

        mineSweeperEngine.initializeGame(gridSize, mineCount);

        userInterface.displayGrid(userInterface.getDisplay().formatGrid(mineSweeperEngine.getGrid()), true);

        GameStates gameState = GameStates.PLAY;

        while (gameState == GameStates.PLAY) {
            Position position = userInterface.getPosition(gridSize);
            gameState = mineSweeperEngine.makeMove(position.row, position.col);

            if (gameState == GameStates.LOST) {
                userInterface.displayMessage(userInterface.getDisplay().getGameOverMessage());
            } else if (gameState == GameStates.WON) {
                userInterface.displayGrid(userInterface.getDisplay().formatGrid(mineSweeperEngine.getGrid()), false);
                userInterface.displayMessage(userInterface.getDisplay().getWinMessage());
            } else {
                int adjacentMines = mineSweeperEngine.getAdjacentMineCount(position.row, position.col);
                userInterface.displayMessage(userInterface.getDisplay().getAdjacentMinesMessage(adjacentMines));
                userInterface.displayGrid(userInterface.getDisplay().formatGrid(mineSweeperEngine.getGrid()), false);
            }
        }
    }


}
