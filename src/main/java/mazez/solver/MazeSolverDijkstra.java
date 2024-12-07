package mazez.solver;

import mazez.Mode;
import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static java.util.Comparator.comparing;
import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.FIND_SHORTEST_PATH_WITH_WALL_JUMP;
import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeSolverDijkstra implements Solver {
    private static final int JUMP_COST = 20;
    private static final int REGULAR_COST = 1;
    boolean[][] visited;

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
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        return findRouteFrom(board.getStartingCell(), board);
    }

    private Route findRouteFrom(Cell cell, Board board) {
        Queue<Route> queue = new PriorityQueue<>(comparing(Route::cost));
        queue.add(new Route(cell, new ArrayList<>(), 0));
        visited[cell.getRow()][cell.getColumn()] = true;

        while (!queue.isEmpty()) {
            Route route = queue.remove();
            Cell currentCell = route.cell();
            visited[currentCell.getRow()][currentCell.getColumn()] = true;
            List<Cell> path = route.path();
            int cost = route.cost();
            if (board.isValidEnding(currentCell.getRow(), currentCell.getColumn())) {
                return route;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                var neighbour = board.getNeighbour(currentCell, direction);
                neighbour.ifPresent(newCell -> {
                    if (!visited[newCell.getRow()][newCell.getColumn()]) {
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
