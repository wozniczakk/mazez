package mazez;

import java.util.ArrayList;
import java.util.List;

import static mazez.Direction.EAST;
import static mazez.Direction.NORTH;
import static mazez.Direction.SOUTH;
import static mazez.Direction.WEST;

public class MazeSolverDFS {
    boolean[][] visited;
    int[] start;
    Cell[][] maze;
    List<Direction> directions = new ArrayList<>(List.of(NORTH, SOUTH, EAST, WEST));
    List<Cell> endingCells = new ArrayList<>();
    int coinsCounter = 0;

    public MazeSolverDFS(int[] start, Cell[][] maze) {
        this.visited = new boolean[maze.length][maze[0].length];
        this.start = start;
        this.maze = maze;
    }

    public void solve() {
        System.out.println("Coins found " + coinsCounter);
    }

    public List<Cell> findEndingCells() {
        // hardcoded starting position to [0,0]
        dfs(0, 0);
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

    private void dfs(int row, int column) {
        if (isValidCell(row, column) && !visited[row][column]) {
            visited[row][column] = true;
            if (isValidEnding(row, column)) {
                endingCells.add(maze[row][column]);
            }
            if (hasCoin(maze[row][column])) {
                coinsCounter++;
            }
            for (Direction direction : directions) {
                if (canGoInDirection(direction, maze[row][column])) {
                    int newRow = row + direction.vector[0];
                    int newColumn = column + direction.vector[1];
                    dfs(newRow, newColumn);
                }
            }
        }
    }

    private boolean isValidCell(int row, int column) {
        return row < maze.length && row >= 0 && column < maze[0].length && column >= 0;
    }

    private boolean isValidEnding(int row, int column) {
        // assuming that we always start at [0,0] cell
        return column == maze[0].length - 1 || row == maze.length - 1;
    }

    private boolean hasCoin(Cell cell) {
        return cell.hasCoin();
    }

    private boolean canGoInDirection(Direction direction, Cell cell) {
        return switch (direction) {
            case NORTH -> cell.hasNPassage();
            case EAST -> cell.hasEPassage();
            case SOUTH -> cell.hasSPassage();
            case WEST -> cell.hasWPassage();
        };
    }
}
