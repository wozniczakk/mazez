package mazez;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static mazez.Direction.EAST;
import static mazez.Direction.NORTH;
import static mazez.Direction.SOUTH;
import static mazez.Direction.WEST;

public class MazeSolverBFS {
    boolean[][] visited;
    int[] start;
    Cell[][] maze;
    List<Direction> directions = new ArrayList<>(List.of(NORTH, SOUTH, EAST, WEST));
    List<Cell> endingCells = new ArrayList<>();

    public MazeSolverBFS(int[] start, Cell[][] maze) {
        this.visited = new boolean[maze.length][maze[0].length];
        this.start = start;
        this.maze = maze;
    }

    public List<Cell> getEndingCells() {
        // hardcoded starting position to [0,0]
        bfs(0, 0);
        int count = 0;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (visited[i][j]) {
                    count++;
                }
            }
        }
        System.out.println("count " + count);
        return endingCells;
    }

//    public int bfs(int row, int column) {
//        Queue<Cell> queue = new LinkedList<>();
//        queue.add(maze[row][column]);
//        visited[row][column] = true;
//        int stepCounter = 0;
//
//        while (!queue.isEmpty()) {
//            int currentQueueSize = queue.size();
//            stepCounter++;
//            for (int i = 0; i < currentQueueSize; i++) {
//                Cell currentCell = queue.remove();
//                if (isValidEnding(currentCell.getX(), currentCell.getY())) {
//                    return stepCounter;
//                }
//                for (Direction direction : directions) {
//                    if (canGoInDirection(direction, maze[currentCell.getX()][currentCell.getY()])) {
//                        int newRow = currentCell.getX() + direction.vector[0];
//                        int newColumn = currentCell.getY() + direction.vector[1];
//                        if (isValidCell(newRow, newColumn) && !visited[newRow][newColumn]) {
//                            queue.add(maze[newRow][newColumn]);
//                            visited[newRow][newColumn] = true;
//                        }
//                    }
//                }
//            }
//        }
//        return stepCounter;
//    }

    public List<Cell> bfs(int row, int column) {
        Queue<CellWithPath> queue = new LinkedList<>();
        queue.add(new CellWithPath(maze[row][column], new ArrayList<>()));
        visited[row][column] = true;

        while (!queue.isEmpty()) {
            int currentQueueSize = queue.size();
            for (int i = 0; i < currentQueueSize; i++) {
                CellWithPath cellWithPath = queue.remove();
                Cell currentCell = cellWithPath.cell();
                List<Cell> path = cellWithPath.path;
                if (isValidEnding(currentCell.getX(), currentCell.getY())) {
                    return path;
                }
                for (Direction direction : directions) {
                    if (canGoInDirection(direction, maze[currentCell.getX()][currentCell.getY()])) {
                        int newRow = currentCell.getX() + direction.vector[0];
                        int newColumn = currentCell.getY() + direction.vector[1];
                        if (isValidCell(newRow, newColumn) && !visited[newRow][newColumn]) {
                            var newPath = new ArrayList<>(path);
                            newPath.add(maze[newRow][newColumn]);
                            queue.add(new CellWithPath(maze[newRow][newColumn], newPath));
                            visited[newRow][newColumn] = true;
                        }
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    private boolean isValidCell(int row, int column) {
        return row < maze.length && row >= 0 && column < maze[0].length && column >= 0;
    }

    private boolean isValidEnding(int row, int column) {
        // assuming that we always start at [0,0] cell
        return column == maze[0].length - 1 || row == maze.length - 1;
    }

    private boolean canGoInDirection(Direction direction, Cell cell) {
        return switch (direction) {
            case NORTH -> cell.hasNPassage();
            case EAST -> cell.hasEPassage();
            case SOUTH -> cell.hasSPassage();
            case WEST -> cell.hasWPassage();
        };
    }

    record CellWithPath(Cell cell, List<Cell> path) {
    }
}
