package mazez.model;

import org.junit.Test;

import java.util.Optional;

import static java.util.Optional.empty;
import static mazez.model.Direction.EAST;
import static mazez.model.Direction.NORTH;
import static mazez.model.Direction.SOUTH;
import static mazez.model.Direction.WEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    Position start = new Position(0, 0);
    int size = 10;
    Board board = new Board(size, size, start);

    @Test
    public void returns_board_properties() {
        assertEquals(board.getStartingPosition(), new Position(0, 0));
        assertEquals(board.getStartingCell(), new Cell(0,0));
        assertEquals(board.getNumberOfRows(), 10);
        assertEquals(board.getNumberOfColumns(), 10);
        assertEquals(board.getAllCells().size(), 100);
        assertEquals(board.getBoard().length, 10);
        assertEquals(board.getBoard()[0].length, 10);
    }

    @Test
    public void validates_ending_position() {
        assertTrue(board.isValidEnding(9, 9));
        assertFalse(board.isValidEnding(8, 8));
        assertFalse(board.isValidEnding(11, 11));
    }

    @Test
    public void gets_neighbour() {
        var cell = new Cell(0, 0);
        var expectedE = new Cell(0,1);
        var expectedN = new Cell(1,0);

        assertEquals(board.getNeighbour(cell, EAST), Optional.of(expectedE));
        assertEquals(board.getNeighbour(cell, NORTH), Optional.of(expectedN));
        assertEquals(board.getNeighbour(cell, WEST), empty());
        assertEquals(board.getNeighbour(cell, SOUTH), empty());
    }
}
