package com.gic.minesweeper.service;

import com.gic.minesweeper.model.Grid;


public interface RandomMineGenerator {
    void placeMines(Grid grid, int mineCount);
}
