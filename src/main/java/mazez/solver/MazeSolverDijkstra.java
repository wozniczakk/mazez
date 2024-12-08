package mazez.solver;

import mazez.Mode;
import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import static java.util.Comparator.comparing;
import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.FIND_SHORTEST_PATH_WITH_WALL_JUMP;
import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeSolverDijkstra implements Solver {
    private static final int JUMP_COST = 20;
    private static final int REGULAR_COST = 1;

    private final Set<Cell> visited = new HashSet<>();

    @Override
    public void printSolution(Board board, Mode mode) {
        if (mode != FIND_SHORTEST_PATH_WITH_WALL_JUMP) {
            throw new UnsupportedOperationException("Cannot solve this mode");
        }
        var shortestPath = solve(board);
        displayMaze(shortestPath.path(), board);
        System.out.println("Shortest path length is " + shortestPath.path().size());
        System.out.println("Cost of the route is " + shortestPath.cost());
    }

    private Route solve(Board board) {
        var route = findRouteFrom(board.getStartingCell(), board);
        visited.clear();

        return route;
    }

    private Route findRouteFrom(Cell cell, Board board) {
        Queue<Route> queue = new PriorityQueue<>(comparing(Route::cost));
        queue.add(new Route(cell, new ArrayList<>(), 0));
        visited.add(cell);

        while (!queue.isEmpty()) {
            Route route = queue.remove();
            Cell currentCell = route.cell();
            visited.add(currentCell);
            List<Cell> path = route.path();
            int cost = route.cost();
            if (board.isValidEnding(currentCell)) {
                return route;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                var neighbour = board.getNeighbour(currentCell, direction);
                neighbour.ifPresent(newCell -> {
                    if (!visited.contains(cell)) {
                        int newCost = cost + calculateCost(currentCell, direction);
                        var newPath = new ArrayList<>(path);
                        newPath.add(newCell);
                        queue.add(new Route(newCell, newPath, newCost));
                    }
                });
            }
        }
        throw new IllegalStateException("The maze cannot be solved");
    }

    private int calculateCost(Cell cell, Direction direction) {
        return cell.canGoInDirection(direction) ? REGULAR_COST : JUMP_COST;
    }

    record Route(Cell cell, List<Cell> path, int cost) {
    }
}
