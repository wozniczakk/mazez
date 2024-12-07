package mazez;

import mazez.model.Board;
import mazez.model.Cell;

import java.util.List;

import static mazez.model.Direction.EAST;

public class MazePrinter {

    public static void displayMaze(List<Cell> path, Board maze) {
        StringBuilder sb = new StringBuilder();
        int size = maze.getNumberOfColumns();
        sb.append(" ");
        sb.repeat("_", size * 2 - 1);
        sb.append('\n');

        for (int row = size - 1; row >= 0; row--) {
            sb.append('|');
            for (int column = 0; column < size; column++) {
                Cell cell = maze.getCell(row, column);
                if (path.contains(cell)) {
                    sb.append("()");
                } else {
                    if (cell.hasCoin()) {
                        sb.append("*");
                    } else if (cell.hasSpikeTrap()) {
                        sb.append("^");
                    } else if (cell.hasSpiderTrap()) {
                        sb.append("S");
                    } else {
                        sb.append(cell.hasSPassage() ? " " : "_");
                    }
                    if (cell.hasEPassage()) {
                        sb.append(cell.hasSPassage() || maze.getNeighbour(cell, EAST).map(Cell::hasSPassage).orElse(false) ? " " : "_");
                    } else {
                        sb.append("|");
                    }
                }
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
