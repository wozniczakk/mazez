package mazez.solver;

import mazez.Mode;
import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.FIND_SHORTEST_PATH;
import static mazez.model.Direction.ALL_DIRECTIONS;

/**
 * Solves the provided maze using Breadth-First Search Algorithm.
 * <p>
 * BFS explanation: <a href="https://en.wikipedia.org/wiki/Breadth-first_search">...</a>
 * <p>
 * The algorithm guarantees to find the shortest path when traversing a graph.
 * This implementation searches for any valid exit on the walls placed diagonally towards the starting point [0,0].
 */
public class MazeSolverBFS implements Solver {
    private final Set<Cell> visited = new HashSet<>();

    @Override
    public void printSolution(Board board, Mode mode) {
        if (mode != FIND_SHORTEST_PATH) {
            throw new UnsupportedOperationException("Cannot solve this mode!");
        }
        var shortestPath = solve(board);
        displayMaze(shortestPath, board);
        System.out.printf("Shortest path length is %s.%n", shortestPath.size());
    }

    private List<Cell> solve(Board board) {
        var result = findRouteFrom(board.getStartingCell(), board);
        visited.clear();

        return result;
    }

    private List<Cell> findRouteFrom(Cell cell, Board board) {
        Queue<Route> queue = new LinkedList<>();
        queue.add(new Route(cell, new ArrayList<>()));
        visited.add(cell);

        while (!queue.isEmpty()) {
            Route route = queue.remove();
            Cell currentCell = route.cell();
            List<Cell> path = route.path;
            if (board.isValidEnding(currentCell)) {
                return path;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                if (currentCell.canGoInDirection(direction)) {
                    var neighbour = board.getNeighbour(currentCell, direction);
                    neighbour.ifPresent(newCell -> {
                        if (!visited.contains(newCell)) {
                            var newPath = new ArrayList<>(path);
                            newPath.add(newCell);
                            queue.add(new Route(newCell, newPath));
                            visited.add(newCell);
                        }
                    });
                }
            }
        }
        throw new IllegalStateException("Maze cannot be solved!");
    }

    record Route(Cell cell, List<Cell> path) {
    }
}
