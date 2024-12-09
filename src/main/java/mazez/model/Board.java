package mazez.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.stream.IntStream.range;

public class Board {

    private final int numberOfRows;
    private final int numberOfColumns;
    private final Position startingPosition;
    private final Cell[][] board;

    public Board(int numberOfRows, int numberOfColumns, Position startingPosition) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.startingPosition = startingPosition;
        this.board = new Cell[numberOfRows][numberOfColumns];
        initialiseBoard();
    }

    private void initialiseBoard() {
        range(0, numberOfRows)
                .forEach(x -> range(0, numberOfColumns)
                        .forEach(y -> board[x][y] = new Cell(x, y)));
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public Cell getStartingCell() {
        return getCell(getStartingPosition());
    }

    public Position getStartingPosition() {
        return startingPosition;
    }

    public Cell[][] getBoard() {
        return board;
    }

    public Optional<Cell> getNeighbour(Cell cell, Direction direction) {
        int newRow = cell.getRow() + direction.vector[0];
        int newColumn = cell.getColumn() + direction.vector[1];
        return isValidCell(newRow, newColumn) ? Optional.of(board[newRow][newColumn]) : empty();
    }

    public Cell getCell(Position position) {
        return board[position.row()][position.column()];
    }

    public Cell getCell(int row, int column) {
        return board[row][column];
    }

    public List<Cell> getAllCells() {
        return new ArrayList<>(Arrays.stream(board).flatMap(Arrays::stream).toList());
    }

    private boolean isValidCell(int row, int column) {
        return row < numberOfRows && row >= 0 && column < numberOfColumns && column >= 0;
    }

    public boolean isValidEnding(Cell cell) {
        return cell.getRow() == numberOfRows - 1 || cell.getColumn() == numberOfColumns - 1;
    }
}
