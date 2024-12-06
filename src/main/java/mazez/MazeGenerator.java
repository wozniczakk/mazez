package mazez;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;
import static java.util.Collections.shuffle;
import static java.util.Optional.empty;
import static java.util.stream.IntStream.range;
import static mazez.Direction.EAST;
import static mazez.Direction.NORTH;
import static mazez.Direction.SOUTH;
import static mazez.Direction.WEST;
import static mazez.Direction.getOpposite;

public class MazeGenerator {
    int size;
    int[] start;
    int numberOfCoins;
    int numberOfObstacles;
    Cell[][] maze;

    public MazeGenerator(GenerationParams generationParams) {
        this.size = generationParams.size;
        this.start = generationParams.start;
        this.numberOfCoins = generationParams.numberOfCoins;
        this.numberOfObstacles = generationParams.numberOfObstacles;
        this.maze = new Cell[size][size];
        initialiseMaze();
    }

    public Cell[][] carve() {
        carvePassageFrom(start[0], start[1]);
        addObstacles(maze, numberOfObstacles);
        addCoins(maze, numberOfCoins);
        return maze;
    }

    private void addCoins(Cell[][] maze, int numberOfCoins) {
        var cellsWithSPassage = new ArrayList<>(Arrays.stream(maze).flatMap(row -> Arrays.stream(row).filter(Cell::hasSPassage)).toList());
        shuffle(cellsWithSPassage);
        range(0, min(numberOfCoins, cellsWithSPassage.size())).forEach(index -> cellsWithSPassage.get(index).setCoin(true));
    }

    private void addObstacles(Cell[][] maze, int numberOfObstacles) {
        var cellsWithSPassage = new ArrayList<>(Arrays.stream(maze).flatMap(Arrays::stream).toList());
        shuffle(cellsWithSPassage);
        range(0, min(numberOfObstacles, cellsWithSPassage.size())).forEach(index -> {
            Cell currentCell = cellsWithSPassage.get(index);
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

    private void closeAllNeighbours(Cell cell) {
        List<Direction> directions = new ArrayList<>(List.of(NORTH, SOUTH, EAST, WEST));
        for (Direction direction : directions) {
            int newRow = cell.getX() + direction.vector[0];
            int newColumn = cell.getY() + direction.vector[1];
            if (isValidCell(newRow, newColumn)) {
                Cell neighbourCell = maze[newRow][newColumn];
                setPassage(neighbourCell, getOpposite(direction), false);
            }
        }
    }

    private void carvePassageFrom(int row, int column) {
        List<Direction> directions = new ArrayList<>(List.of(NORTH, SOUTH, EAST, WEST));
        shuffle(directions);

        for (Direction direction : directions) {
            int newRow = row + direction.vector[0];
            int newColumn = column + direction.vector[1];
            if (isValidCell(newRow, newColumn) && hasNeverBeenVisited(maze[newRow][newColumn])) {
                Cell currentCell = maze[row][column];
                Cell newCell = maze[newRow][newColumn];

                setPassage(currentCell, direction, true);
                setPassage(newCell, getOpposite(direction), true);

                carvePassageFrom(newRow, newColumn);
            }
        }
    }

    private boolean isValidCell(int row, int column) {
        return row < size && row >= 0 && column < size && column >= 0;
    }

    private boolean hasNeverBeenVisited(Cell cell) {
        return !cell.hasEPassage() && !cell.hasNPassage() && !cell.hasSPassage() && !cell.hasWPassage();
    }

    private void initialiseMaze() {
        range(0, size)
                .forEach(x -> range(0, size)
                        .forEach(y -> maze[x][y] = new Cell(x, y)));
    }

    private void setPassage(Cell cell, Direction direction, boolean isOpen) {
        switch (direction) {
            case NORTH -> cell.setHasNPassage(isOpen);
            case SOUTH -> cell.setHasSPassage(isOpen);
            case EAST -> cell.setHasEPassage(isOpen);
            case WEST -> cell.setHasWPassage(isOpen);
        }
    }

    private Optional<Cell> getRightNeighbour(int row, int column) {
        int neighbourColumn = column + 1;
        return isValidCell(row, neighbourColumn) ? Optional.of(maze[row][column + 1]) : empty();
    }

    public void displayMaze(List<Cell> path) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.repeat("_", size * 2 - 1);
        sb.append('\n');

        for (int row = size - 1; row >= 0; row--) {
            sb.append('|');
            for (int column = 0; column < size; column++) {
                Cell cell = maze[row][column];
                if (path.contains(cell)) {
                    sb.append("()");
                } else {
                    if (cell.hasCoin()) {
                        sb.append("*");
                    } else {
                        sb.append(cell.hasSPassage() ? " " : "_");
                    }
                    if (cell.hasEPassage()) {
                        sb.append(cell.hasSPassage() || getRightNeighbour(row, column).map(Cell::hasSPassage).orElse(false) ? " " : "_");
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
    public record GenerationParams(int size, int[] start, int numberOfCoins, int numberOfObstacles) {
    }
}




