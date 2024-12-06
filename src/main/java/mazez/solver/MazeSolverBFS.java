package mazez.solver;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;
import mazez.Mode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static mazez.model.Direction.ALL_DIRECTIONS;
import static mazez.MazeGenerator.displayMaze;
import static mazez.Mode.FIND_SHORTEST_PATH;

public class MazeSolverBFS implements Solver {
    boolean[][] visited;

    @Override
    public void printSolution(Board board, Mode mode) {
        if(mode!= FIND_SHORTEST_PATH){
            throw new UnsupportedOperationException("Cannot solve this mode");
        }
        var shortestPath = solve(board);
        displayMaze(shortestPath, board);
        System.out.println("Shortest path length is " + shortestPath.size());
    }

    public List<Cell> solve(Board board) {
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        return bfs(board.getCell(board.getStartingPosition()), board);
    }

    private List<Cell> bfs(Cell cell, Board board) {
        Queue<CellWithPath> queue = new LinkedList<>();
        queue.add(new CellWithPath(cell, new ArrayList<>()));
        visited[cell.getX()][cell.getY()] = true;

        while (!queue.isEmpty()) {
            int currentQueueSize = queue.size();
            for (int i = 0; i < currentQueueSize; i++) {
                CellWithPath cellWithPath = queue.remove();
                Cell currentCell = cellWithPath.cell();
                List<Cell> path = cellWithPath.path;
                if (board.isValidEnding(currentCell.getX(), currentCell.getY())) {
                    return path;
                }
                for (Direction direction : ALL_DIRECTIONS) {
                    if (currentCell.canGoInDirection(direction)) {
                        var neighbour = board.getNeighbour(currentCell, direction);
                        neighbour.ifPresent(newCell -> {
                            if (!visited[newCell.getX()][newCell.getY()]) {
                                var newPath = new ArrayList<>(path);
                                newPath.add(newCell);
                                queue.add(new CellWithPath(newCell, newPath));
                                visited[newCell.getX()][newCell.getY()] = true;
                            }
                        });
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    record CellWithPath(Cell cell, List<Cell> path) {
    }
}
