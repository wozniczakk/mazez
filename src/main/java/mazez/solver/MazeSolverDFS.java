package mazez.solver;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Direction;
import mazez.Mode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static mazez.model.Direction.ALL_DIRECTIONS;

public class MazeSolverDFS implements Solver {
    private final Set<Cell> visited = new HashSet<>();
    private final List<Cell> endingCells = new ArrayList<>();
    private int coinsCounter = 0;

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
        visited.clear();
        endingCells.clear();
        coinsCounter = 0;

        findSolutionFrom(board.getStartingCell(), board);
    }

    private void findSolutionFrom(Cell cell, Board board) {
        if (!visited.contains(cell)) {
            visited.add(cell);
            if (board.isValidEnding(cell)) {
                endingCells.add(cell);
            }
            if (cell.hasCoin()) {
                coinsCounter++;
            }
            for (Direction direction : ALL_DIRECTIONS) {
                if (cell.canGoInDirection(direction)) {
                    findSolutionFrom(board.getNeighbour(cell, direction).orElseThrow(), board);
                }
            }
        }
    }
}
