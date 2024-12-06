package mazez.solver;

import mazez.Board;
import mazez.Cell;
import mazez.Direction;
import mazez.Mode;

import java.util.ArrayList;
import java.util.List;

import static mazez.Direction.ALL_DIRECTIONS;

public class MazeSolverDFS implements Solver {
    boolean[][] visited;
    List<Cell> endingCells = new ArrayList<>();
    int coinsCounter = 0;

    @Override
    public void printSolution(Board board, Mode mode) {
        solve(board);
        switch (mode) {
            case COLLECT_COINS -> System.out.println("Coins found " + coinsCounter);
            case FIND_ALL_EXITS -> System.out.println("Exits found " + endingCells.size());
            default -> throw new UnsupportedOperationException("Cannot solve this mode");
        }
    }

    private void solve(Board board) {
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        dfs(board.getCell(board.getStartingPosition()), board);
    }

    private void dfs(Cell cell, Board board) {
        int row = cell.getX();
        int column = cell.getY();
        if (!visited[row][column]) {
            visited[row][column] = true;
            if (board.isValidEnding(row, column)) {
                endingCells.add(cell);
            }
            if (hasCoin(cell)) {
                coinsCounter++;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                if (cell.canGoInDirection(direction)) {
                    int newRow = row + direction.vector[0];
                    int newColumn = column + direction.vector[1];
                    dfs(board.getBoard()[newRow][newColumn], board);
                }
            }
        }
    }

    private boolean hasCoin(Cell cell) {
        return cell.hasCoin();
    }
}