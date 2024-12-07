package mazez.solver;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;
import mazez.Mode;

import java.util.ArrayList;
import java.util.List;

import static mazez.model.Direction.ALL_DIRECTIONS;

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
        findSolutionFrom(board.getStartingCell(), board);
    }

    private void findSolutionFrom(Cell cell, Board board) {
        int row = cell.getRow();
        int column = cell.getColumn();
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
                    findSolutionFrom(board.getBoard()[newRow][newColumn], board);
                }
            }
        }
    }

    private boolean hasCoin(Cell cell) {
        return cell.hasCoin();
    }
}
