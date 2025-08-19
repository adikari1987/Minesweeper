package com.gic.minesweeper.view;

import com.gic.minesweeper.model.Position;
import com.gic.minesweeper.service.InputValidator;
import com.gic.minesweeper.service.impl.InputValidatorImpl;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserInterfaceImpl implements UserInterface {

    private final Scanner scanner;
    private final PrintStream output;
    private final InputValidator validator;
    private final GameConsole gameConsole;

    public UserInterfaceImpl(InputStream input, PrintStream output) {
        this.scanner = new Scanner(input);
        this.output = output;
        this.validator = new InputValidatorImpl();
        this.gameConsole = new GameConsole();
    }

    public int getGridSize() {
        output.println(gameConsole.getGridSizePrompt());

        while (true) {
            String input = scanner.nextLine();
            if (validator.isValidGridSize(input)) {
                return Integer.parseInt(input);
            }
            output.println("Invalid input. Please enter a positive number:");
        }
    }

    public int getMineCount(int gridSize) {
        output.println(gameConsole.getMineCountPrompt());
        int maxMines = (int) (gridSize * gridSize * 0.35);
        while (true) {
            String input = scanner.nextLine();
            if (validator.isValidMineCount(input, gridSize)) {
                return Integer.parseInt(input);
            }
            output.println("Invalid input. Please enter a positive number. maximum is " + maxMines + ":");
        }
    }

    public Position getPosition(int gridSize) {
        output.println(gameConsole.getPositionPrompt());

        while (true) {
            String input = scanner.nextLine();
            if (validator.isValidPosition(input, gridSize)) {
                return validator.parsePosition(input, gridSize);
            }
            output.println("Invalid input. Please enter position like A1, B2:");
        }
    }

    public void displayMessage(String message) {
        output.println(message);
    }

    public void displayGrid(String gridString, boolean isInitial) {
        if (isInitial) {
            displayMessage(gameConsole.getHeader());
        } else {
            displayMessage(gameConsole.getUpdatedHeader());
        }
        output.print(gridString);
    }

    public void waitForPlayAgain() {
        displayMessage(gameConsole.getPlayAgainPrompt());
        scanner.nextLine();
    }

    public GameConsole getDisplay() {
        return gameConsole;
    }

}
