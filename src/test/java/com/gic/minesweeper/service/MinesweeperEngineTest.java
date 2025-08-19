package com.gic.minesweeper.service;

import com.gic.minesweeper.model.GameStates;
import com.gic.minesweeper.model.Grid;
import com.gic.minesweeper.service.impl.MinesweeperEngineImpl;
import com.gic.minesweeper.service.impl.RandomMineGeneratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MinesweeperEngineTest {

    private MinesweeperEngineImpl engine;

    @Mock
    private RandomMineGenerator mockMineGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        engine = new MinesweeperEngineImpl(mockMineGenerator);
    }

    @Test
    void testInitializeGame() {
        engine.initializeGame(4, 3);

        assertNotNull(engine.getGrid());
        assertEquals(4, engine.getGrid().getSize());
        assertEquals(GameStates.PLAY, engine.getGameState());
        verify(mockMineGenerator).placeMines(any(Grid.class), eq(3));
    }

    @Test
    void testMakeMoveOnSafeCell() {
        engine.initializeGame(3, 0); // No mines

        GameStates result = engine.makeMove(1, 1);

        assertEquals(GameStates.WON, result);
        assertTrue(engine.getGrid().getCell(1, 1).isRevealed());
    }

    @Test
    void testMakeMoveOnMine() {
        engine.initializeGame(3, 1);
        // Manually place a mine for testing
        engine.getGrid().placeMine(1, 1);

        GameStates result = engine.makeMove(1, 1);

        assertEquals(GameStates.LOST, result);
        assertTrue(engine.getGrid().getCell(1, 1).isRevealed());
    }

    @Test
    void testWinCondition() {
        engine.initializeGame(2, 1);
        engine.getGrid().placeMine(0, 0);
        engine.getGrid().calculateAdjacentMines();

        // Reveal all non-mine cells
        engine.makeMove(0, 1);
        engine.makeMove(1, 0);
        GameStates result = engine.makeMove(1, 1);

        assertEquals(GameStates.WON, result);
    }

    @Test
    void testMakeMoveOnAlreadyRevealedCell() {
        engine.initializeGame(3, 0);
        engine.makeMove(1, 1); // First move

        GameStates result = engine.makeMove(1, 1); // Same move again

        assertEquals(GameStates.WON, result);
    }

    @Test
    void testMakeMoveWhenGameOver() {
        engine.initializeGame(3, 1);
        engine.getGrid().placeMine(0, 0);
        engine.makeMove(0, 0); // Hit mine - game over

        GameStates result = engine.makeMove(1, 1); // Try another move

        assertEquals(GameStates.LOST, result);
    }

    @Test
    void testGetAdjacentMineCount() {
        engine.initializeGame(3, 1);
        engine.getGrid().placeMine(0, 0);
        engine.getGrid().calculateAdjacentMines();

        int count = engine.getAdjacentMineCount(1, 1);

        assertEquals(1, count);
    }

    @Test
    void testDefaultConstructor() {
        RandomMineGenerator randomMineGenerator = new RandomMineGeneratorImpl(new Random());
        MinesweeperEngine defaultEngine = new MinesweeperEngineImpl(randomMineGenerator);
        assertNotNull(defaultEngine);
        assertEquals(GameStates.PLAY, defaultEngine.getGameState());
    }

}
