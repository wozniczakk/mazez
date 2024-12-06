package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;
import mazez.model.Position;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;
import static mazez.model.Direction.ALL_DIRECTIONS;
import static mazez.model.Direction.EAST;
import static mazez.model.Direction.getOpposite;

public class MazeGenerator {
    public static final Position DEFAULT_STARTING_POSITION = new Position(0, 0);
    int size;
    Position startingPosition;
    int numberOfCoins;
    int numberOfObstacles;
    Board maze;

    public MazeGenerator(GenerationParams generationParams) {
        this.size = generationParams.size;
        this.startingPosition = generationParams.start;
        this.numberOfCoins = generationParams.numberOfCoins;
        this.numberOfObstacles = generationParams.numberOfObstacles;
        this.maze = new Board(size, size, generationParams.start);
    }

    public Board carve() {
        carvePassageFrom(maze.getCell(maze.getStartingPosition()));
        addObstacles(maze, numberOfObstacles);
        addCoins(maze, numberOfCoins);
        return maze;
    }

    private void addCoins(Board maze, int numberOfCoins) {
        var cellsWithSPassage = maze.getAllCells().stream().filter(Cell::hasSPassage).collect(toCollection(ArrayList::new));
        shuffle(cellsWithSPassage);
        range(0, min(numberOfCoins, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setCoin(true));
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

    private boolean hasNeverBeenVisited(Cell cell) {
        return !cell.hasEPassage() && !cell.hasNPassage() && !cell.hasSPassage() && !cell.hasWPassage();
    }

    private void setPassage(Cell cell, Direction direction, boolean isOpen) {
        switch (direction) {
            case NORTH -> cell.setHasNPassage(isOpen);
            case SOUTH -> cell.setHasSPassage(isOpen);
            case EAST -> cell.setHasEPassage(isOpen);
            case WEST -> cell.setHasWPassage(isOpen);
        }
    }

    public static void displayMaze(List<Cell> path, Board maze) {
        StringBuilder sb = new StringBuilder();
        int size = maze.getNumberOfColumns();
        sb.append(" ");
        sb.repeat("_", size * 2 - 1);
        sb.append('\n');

        for (int row = size - 1; row >= 0; row--) {
            sb.append('|');
            for (int column = 0; column < size; column++) {
                Cell cell = maze.getCell(row, column);
                if (path.contains(cell)) {
                    sb.append("()");
                } else {
                    if (cell.hasCoin()) {
                        sb.append("*");
                    } else {
                        sb.append(cell.hasSPassage() ? " " : "_");
                    }
                    if (cell.hasEPassage()) {
                        sb.append(cell.hasSPassage() || maze.getNeighbour(cell, EAST).map(Cell::hasSPassage).orElse(false) ? " " : "_");
                    } else {
                        sb.append("|");
                    }
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }

    //enter/exit/obstacles
    public record GenerationParams(int size, Position start, int numberOfCoins, int numberOfObstacles) {
    }
}




