package mazez;

import java.util.ArrayList;
import java.util.List;

import static mazez.Direction.ALL_DIRECTIONS;

public class MazeValidator {
    List<Cell> endingCells;
    boolean[][] visited;

    public boolean validate(Board board) {
        visited = new boolean[board.getNumberOfRows()][board.getNumberOfColumns()];
        endingCells = new ArrayList<>();
        dfs(board.getCell(board.getStartingPosition()), board);
        return !endingCells.isEmpty();
    }

    private void dfs(Cell cell, Board board) {
        int row = cell.getX();
        int column = cell.getY();
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
