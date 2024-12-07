package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;
import mazez.model.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;
import static java.util.stream.IntStream.range;
import static mazez.model.Direction.ALL_DIRECTIONS;
import static mazez.model.Direction.getOpposite;

public class MazeGenerator {
    public static final Position DEFAULT_STARTING_POSITION = new Position(0, 0);
    int size;
    Position startingPosition;
    int numberOfObstacles;
    Board maze;

    public MazeGenerator(GenerationParams generationParams) {
        this.size = generationParams.size;
        this.startingPosition = generationParams.start;
        this.numberOfObstacles = generationParams.numberOfObstacles;
        this.maze = new Board(size, size, generationParams.start);
    }

    public Board carve() {
        carvePassageFrom(maze.getStartingCell());
        addObstacles(maze, numberOfObstacles);
        return maze;
    }

    private void addObstacles(Board maze, int numberOfObstacles) {
        var cells = maze.getAllCells();
        shuffle(cells);
        range(0, min(numberOfObstacles, cells.size())).forEach(index -> {
            Cell currentCell = cells.get(index);
            closePassage(currentCell);
            closeAllNeighbours(currentCell);
        });
    }

    private void closePassage(Cell cell) {
        cell.setHasNPassage(false);
        cell.setHasSPassage(false);
        cell.setHasEPassage(false);
        cell.setHasWPassage(false);
    }

    private void closeAllNeighbours(Cell currentCell) {
        for (Direction direction : ALL_DIRECTIONS) {
            var neighbour = maze.getNeighbour(currentCell, direction);
            neighbour.ifPresent(newCell -> setPassage(newCell, getOpposite(direction), false));
        }
    }

    private void carvePassageFrom(Cell currentCell) {
        List<Direction> directions = new ArrayList<>(ALL_DIRECTIONS);
        shuffle(directions);

        for (Direction direction : directions) {
            var neighbour = maze.getNeighbour(currentCell, direction);
            neighbour.filter(this::hasNeverBeenVisited).ifPresent(newCell -> {
                setPassage(currentCell, direction, true);
                setPassage(newCell, getOpposite(direction), true);
                carvePassageFrom(newCell);
            });
        }
    }

    private void setPassage(Cell cell, Direction direction, boolean isOpen) {
        switch (direction) {
            case NORTH -> cell.setHasNPassage(isOpen);
            case SOUTH -> cell.setHasSPassage(isOpen);
            case EAST -> cell.setHasEPassage(isOpen);
            case WEST -> cell.setHasWPassage(isOpen);
        }
    }

    private boolean hasNeverBeenVisited(Cell cell) {
        return !cell.hasEPassage() && !cell.hasNPassage() && !cell.hasSPassage() && !cell.hasWPassage();
    }

    public record GenerationParams(int size, Position start, int numberOfObstacles) {
    }
}
