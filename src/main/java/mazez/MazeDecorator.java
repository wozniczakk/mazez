package mazez;

import mazez.model.Board;
import mazez.model.Cell;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;

public class MazeDecorator {

    private MazeDecorator() {
    }

    public static void addCoins(Board maze, int numberOfCoins) {
        var cellsWithSPassage = shuffleCells(maze);
        range(0, min(numberOfCoins, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setCoin(true));
    }

    public static void addTraps(Board maze, int numberOfSpiderTraps, int numberOfSpikeTraps) {
        var cellsWithSPassage = shuffleCells(maze);
        range(0, min(numberOfSpiderTraps, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setHasSpiderTrap(true));
        range(numberOfSpiderTraps, min(numberOfSpikeTraps + numberOfSpiderTraps, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setHasSpikeTrap(true));
    }

    private static List<Cell> shuffleCells(Board maze) {
        var cellsWithSPassage = maze.getAllCells().stream().filter(Cell::hasSPassage).collect(toCollection(ArrayList::new));
        shuffle(cellsWithSPassage);
        return cellsWithSPassage;
    }
}
