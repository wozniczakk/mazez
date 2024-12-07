package mazez.solver;

import mazez.Mode;
import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.FIND_SHORTEST_PATH;
import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeSolverBFS implements Solver {
    boolean[][] visited;

    @Override
    public void printSolution(Board board, Mode mode) {
        if (mode != FIND_SHORTEST_PATH) {
            throw new UnsupportedOperationException("Cannot solve this mode");
        }
        var shortestPath = solve(board);
        displayMaze(shortestPath, board);
        System.out.println("Shortest path length is " + shortestPath.size());
    }

    private List<Cell> solve(Board board) {
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        return findRouteFrom(board.getStartingCell(), board);
    }

    private List<Cell> findRouteFrom(Cell cell, Board board) {
        Queue<Route> queue = new LinkedList<>();
        queue.add(new Route(cell, new ArrayList<>()));
        visited[cell.getRow()][cell.getColumn()] = true;

        while (!queue.isEmpty()) {
            Route route = queue.remove();
            Cell currentCell = route.cell();
            List<Cell> path = route.path;
            if (board.isValidEnding(currentCell.getRow(), currentCell.getColumn())) {
                return path;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                if (currentCell.canGoInDirection(direction)) {
                    var neighbour = board.getNeighbour(currentCell, direction);
                    neighbour.ifPresent(newCell -> {
                        if (!visited[newCell.getRow()][newCell.getColumn()]) {
                            var newPath = new ArrayList<>(path);
                            newPath.add(newCell);
                            queue.add(new Route(newCell, newPath));
                            visited[newCell.getRow()][newCell.getColumn()] = true;
                        }
                    });
                }
            }
        }
        return new ArrayList<>();
    }

    record Route(Cell cell, List<Cell> path) {
    }
}
