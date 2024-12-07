package mazez.model;

import org.junit.Test;

import static mazez.model.Direction.EAST;
import static mazez.model.Direction.NORTH;
import static mazez.model.Direction.SOUTH;
import static mazez.model.Direction.WEST;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CellTest {

    @Test
    public void validates_passage() {
        var cell = new Cell(0, 0);
        cell.setHasNPassage(true);
        cell.setHasWPassage(true);

        assertTrue(cell.hasNPassage());
        assertTrue(cell.hasWPassage());
        assertFalse(cell.hasSPassage());
        assertFalse(cell.hasEPassage());
    }

    @Test
    public void validates_direction(){
        var cell = new Cell(0, 0);
        cell.setHasEPassage(true);
        cell.setHasSPassage(true);

        assertTrue(cell.canGoInDirection(EAST));
        assertTrue(cell.canGoInDirection(SOUTH));
        assertFalse(cell.canGoInDirection(NORTH));
        assertFalse(cell.canGoInDirection(WEST));
    }
}
