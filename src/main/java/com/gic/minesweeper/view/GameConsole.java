package com.gic.minesweeper.view;

import com.gic.minesweeper.model.Cell;
import com.gic.minesweeper.model.Grid;

public class GameConsole {

    public String formatGrid(Grid grid) {
        StringBuilder sb = new StringBuilder();
        int size = grid.getSize();

        // Header row with column numbers
        sb.append("  ");
        for (int col = 1; col <= size; col++) {
            sb.append(col).append(" ");
        }
        sb.append("\n");

        // Grid rows with row letters
        for (int row = 0; row < size; row++) {
            char rowLetter = (char) ('A' + row);
            sb.append(rowLetter).append(" ");

            for (int col = 0; col < size; col++) {
                Cell cell = grid.getCell(row, col);
                sb.append(cell.toString()).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public String getWelcomeMessage() {
        return "Welcome to Minesweeper!\n";
    }

    public String getGridSizePrompt() {
        return "Enter the size of the grid (e.g. 4 for a 4x4 grid):";
    }

    public String getMineCountPrompt() {
        return "Enter the number of mines to place on the grid (maximum is 35% of the total squares):";
    }

    public String getPositionPrompt() {
        return "\nSelect a square to reveal (e.g. A1):";
    }

    public String getAdjacentMinesMessage(int count) {
        return "This square contains " + count + " adjacent mines.";
    }

    public String getGameOverMessage() {
        return "Oh no, you detonated a mine! Game over.";
    }

    public String getWinMessage() {
        return "Congratulations, you have won the game!";
    }

    public String getPlayAgainPrompt() {
        return "Press any key to play again...";
    }

    public String getHeader() {
        return "\nHere is your minefield:";
    }

    public String getUpdatedHeader() {
        return "\nHere is your updated minefield:";
    }

}
