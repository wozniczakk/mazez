package mazez.solver;

import mazez.Mode;
import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.TRAPS;
import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeSolverBellmanFord implements Solver {

    private final static int SPIKE_TRAP = 15;
    private final static int SPIDER_TRAP = 30;

    @Override
    public void printSolution(Board board, Mode mode) {
        if (mode != TRAPS) {
            throw new UnsupportedOperationException("Cannot solve this mode");
        }
        var shortestPath = solve(board);
        displayMaze(shortestPath.path(), board);
        System.out.println("Shortest path length is " + shortestPath.path.size());
        System.out.println("Best score is " + -shortestPath.score());
    }

    private Route solve(Board board) {
        return findOptimalRouteFrom(board);
    }

    private Route findOptimalRouteFrom(Board board) {
        var distances = board.getAllCells().stream().collect(toMap(key -> key, key -> 10000));
        var predecessors = board.getAllCells().stream().collect(toMap(key -> key, key -> key));
        distances.put(board.getStartingCell(), 0);

        for (int i = 0; i < board.getAllCells().size() - 1; i++) {
            for (Cell cell : board.getAllCells()) {
                for (Direction direction : ALL_DIRECTIONS) {
                    if (cell.canGoInDirection(direction)) {
                        var neighbour = board.getNeighbour(cell, direction);
                        neighbour.ifPresent(newCell -> {
                            int distance = distances.get(cell) + calculateCellPoints(newCell);
                            if (distance < distances.get(newCell)) {
                                distances.put(newCell, distance);
                                predecessors.put(newCell, cell);
                            }
                        });
                    }
                }
            }
        }

        for (Cell cell : board.getAllCells()) {
            for (Direction direction : ALL_DIRECTIONS) {
                if (cell.canGoInDirection(direction)) {
                    var neighbour = board.getNeighbour(cell, direction);
                    neighbour.ifPresent(newCell -> {
                        int distance = distances.get(cell) + calculateCellPoints(newCell);
                        if (distance < distances.get(newCell)) {
                            throw new IllegalStateException("Maze cannot be solved. Negative cycle found!");
                        }
                    });
                }
            }
        }

        var endingCells = board.getAllCells().stream().filter(cell -> board.isValidEnding(cell.getRow(), cell.getColumn())).toList();
        var min = distances.entrySet().stream().filter(entry -> endingCells.contains(entry.getKey())).min(Map.Entry.comparingByValue());
        var path = calculatePath(predecessors, board.getStartingCell(), min.orElseThrow().getKey());
        return new Route(min.orElseThrow().getValue(), path);
    }

    private int calculateCellPoints(Cell cell) {
        if (cell.hasSpiderTrap()) {
            return SPIKE_TRAP;
        } else if (cell.hasSpikeTrap()) {
            return SPIDER_TRAP;
        } else {
            return 0;
        }
    }

    private List<Cell> calculatePath(Map<Cell, Cell> predecessors, Cell start, Cell from) {
        var path = new ArrayList<Cell>();
        var current = from;

        while (!current.getPosition().equals(start.getPosition())) {
            path.add(current);
            current = predecessors.get(current);
        }

        path.add(current);
        return path;
    }

    record Route(int score, List<Cell> path) {
    }
}
