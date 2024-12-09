package mazez.model;

import org.junit.jupiter.api.Test;

import static mazez.model.Direction.EAST;
import static mazez.model.Direction.NORTH;
import static mazez.model.Direction.SOUTH;
import static mazez.model.Direction.WEST;
import static org.assertj.core.api.Assertions.assertThat;


public class BoardTest {

    Position start = new Position(0, 0);
    int size = 10;
    Board board = new Board(size, size, start);

    @Test
    public void returns_board_properties() {
        assertThat(board.getStartingPosition()).isEqualTo(new Position(0, 0));
        assertThat(board.getStartingCell()).isEqualTo(new Cell(0, 0));
        assertThat(board.getNumberOfRows()).isEqualTo(10);
        assertThat(board.getNumberOfColumns()).isEqualTo(10);
        assertThat(board.getAllCells()).hasSize(100);
        assertThat(board.getBoard()).hasDimensions(10, 10);
    }

    @Test
    public void validates_ending_position() {
        assertThat(board.isValidEnding(new Cell(9, 9))).isTrue();
        assertThat(board.isValidEnding(new Cell(8, 8))).isFalse();
        assertThat(board.isValidEnding(new Cell(11, 11))).isFalse();
    }

    @Test
    public void gets_neighbour() {
        var cell = new Cell(0, 0);
        var expectedE = new Cell(0, 1);
        var expectedN = new Cell(1, 0);

        assertThat(board.getNeighbour(cell, EAST)).hasValue(expectedE);
        assertThat(board.getNeighbour(cell, NORTH)).hasValue(expectedN);
        assertThat(board.getNeighbour(cell, WEST)).isEmpty();
        assertThat(board.getNeighbour(cell, SOUTH)).isEmpty();
    }
}
