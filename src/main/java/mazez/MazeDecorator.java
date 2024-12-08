package mazez;

import mazez.model.Board;
import mazez.model.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;

public class MazeDecorator {

    private MazeDecorator() {
    }

    public static void addCoins(Board maze, int coins, Random random) {
        var cellsWithSPassage = shuffleCells(maze, random);
        range(0, min(coins, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setCoin(true));
    }

    public static void addTraps(Board maze, int spiderTraps, int numberOfSpikeTraps, Random random) {
        var cellsWithSPassage = shuffleCells(maze, random);
        range(0, min(spiderTraps, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setSpiderTrap(true));
        range(spiderTraps, min(numberOfSpikeTraps + spiderTraps, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setSpikeTrap(true));
    }

    private static List<Cell> shuffleCells(Board maze, Random random) {
        var cellsWithSPassage = maze.getAllCells().stream().filter(Cell::hasSPassage).collect(toCollection(ArrayList::new));
        shuffle(cellsWithSPassage, random);
        return cellsWithSPassage;
    }
}
