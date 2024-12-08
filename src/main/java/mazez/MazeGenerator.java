package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;
import mazez.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;
import static java.util.stream.IntStream.range;
import static mazez.model.Direction.ALL_DIRECTIONS;
import static mazez.model.Direction.getOpposite;

public class MazeGenerator {
    public static final Position DEFAULT_STARTING_POSITION = new Position(0, 0);
    private final int numberOfObstacles;
    private final Board maze;
    private final Random random;

    public MazeGenerator(GenerationParams generationParams, Random random) {
        int size = generationParams.size;
        this.numberOfObstacles = generationParams.numberOfObstacles;
        this.maze = new Board(size, size, generationParams.start);
        this.random = random;
    }

    public Board carve() {
        carvePassageFrom(maze.getStartingCell());
        addObstacles(maze, numberOfObstacles, random);
        return maze;
    }

    private void addObstacles(Board maze, int numberOfObstacles, Random random) {
        var cells = maze.getAllCells();
        shuffle(cells, random);
        range(0, min(numberOfObstacles, cells.size())).forEach(index -> {
            Cell currentCell = cells.get(index);
            closePassage(currentCell);
            closeAllNeighbours(currentCell);
        });
    }

    private void closePassage(Cell cell) {
        cell.setNPassage(false);
        cell.setSPassage(false);
        cell.setEPassage(false);
        cell.setWPassage(false);
    }

    private void closeAllNeighbours(Cell currentCell) {
        for (Direction direction : ALL_DIRECTIONS) {
            var neighbour = maze.getNeighbour(currentCell, direction);
            neighbour.ifPresent(newCell -> modifyPassage(newCell, getOpposite(direction), false));
        }
    }

    private void carvePassageFrom(Cell currentCell) {
        List<Direction> directions = new ArrayList<>(ALL_DIRECTIONS);
        shuffle(directions, random);

        for (Direction direction : directions) {
            var neighbour = maze.getNeighbour(currentCell, direction);
            neighbour.filter(this::hasNeverBeenVisited).ifPresent(newCell -> {
                modifyPassage(currentCell, direction, true);
                modifyPassage(newCell, getOpposite(direction), true);
                carvePassageFrom(newCell);
            });
        }
    }

    private void modifyPassage(Cell cell, Direction direction, boolean isOpen) {
        switch (direction) {
            case NORTH -> cell.setNPassage(isOpen);
            case SOUTH -> cell.setSPassage(isOpen);
            case EAST -> cell.setEPassage(isOpen);
            case WEST -> cell.setWPassage(isOpen);
        }
    }

    private boolean hasNeverBeenVisited(Cell cell) {
        return !cell.hasEPassage() && !cell.hasNPassage() && !cell.hasSPassage() && !cell.hasWPassage();
    }

    public record GenerationParams(int size, Position start, int numberOfObstacles) {
    }
}
