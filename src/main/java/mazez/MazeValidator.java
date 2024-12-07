package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.ArrayList;
import java.util.List;

import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeValidator {
    List<Cell> endingCells;
    boolean[][] visited;

    public boolean validate(Board board) {
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        endingCells = new ArrayList<>();
        dfs(board.getStartingCell(), board);
        return !endingCells.isEmpty();
    }

    private void dfs(Cell cell, Board board) {
        int row = cell.getRow();
        int column = cell.getColumn();
        if (!visited[row][column]) {
            visited[row][column] = true;
            if (board.isValidEnding(row, column)) {
                endingCells.add(cell);
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
}
