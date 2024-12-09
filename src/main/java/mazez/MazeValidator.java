package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;

import java.util.HashSet;
import java.util.Set;

import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeValidator {
    private final Set<Cell> visited = new HashSet<>();

    public boolean validate(Board board) {
        var result = canReachEnd(board.getStartingCell(), board);
        visited.clear();

        return result;
    }

    private boolean canReachEnd(Cell cell, Board board) {
        if (!visited.contains(cell)) {
            visited.add(cell);
            if (board.isValidEnding(cell)) {
                return true;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                if (cell.canGoInDirection(direction)) {
                    var neighbour = board.getNeighbour(cell, direction).orElseThrow();
                    if(canReachEnd(neighbour, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
