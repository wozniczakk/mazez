package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeValidator {
    private boolean[][] visited;

    public boolean validate(Board board) {
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        return canReachEnd(board.getStartingCell(), board);
    }

    private boolean canReachEnd(Cell cell, Board board) {
        int row = cell.getRow();
        int column = cell.getColumn();
        if (!visited[row][column]) {
            visited[row][column] = true;
            if (board.isValidEnding(row, column)) {
                return true;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                if (cell.canGoInDirection(direction)) {
                    int newRow = row + direction.vector[0];
                    int newColumn = column + direction.vector[1];
                    if(canReachEnd(board.getBoard()[newRow][newColumn], board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
