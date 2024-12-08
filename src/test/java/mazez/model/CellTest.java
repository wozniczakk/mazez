package mazez.model;

import org.junit.jupiter.api.Test;

import static mazez.model.Direction.EAST;
import static mazez.model.Direction.NORTH;
import static mazez.model.Direction.SOUTH;
import static mazez.model.Direction.WEST;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CellTest {

    @Test
    public void sets_passage() {
        var cell = new Cell(0, 0);
        cell.setNPassage(true);
        cell.setWPassage(true);

        assertTrue(cell.hasNPassage());
        assertTrue(cell.hasWPassage());
        assertFalse(cell.hasSPassage());
        assertFalse(cell.hasEPassage());
    }

    @Test
    public void can_go_only_in_direction_with_passage() {
        var cell = new Cell(0, 0);
        cell.setEPassage(true);
        cell.setSPassage(true);

        assertTrue(cell.canGoInDirection(EAST));
        assertTrue(cell.canGoInDirection(SOUTH));
        assertFalse(cell.canGoInDirection(NORTH));
        assertFalse(cell.canGoInDirection(WEST));
    }

    @Test
    public void sets_traps(){
        var cell = new Cell(1, 1);
        cell.setSpiderTrap(true);
        cell.setSpikeTrap(true);

        assertTrue(cell.hasSpiderTrap());
        assertTrue(cell.hasSpikeTrap());
    }

    @Test
    public void sets_coins(){
        var cell = new Cell(1, 1);
        cell.setCoin(true);

        assertTrue(cell.hasCoin());
    }
}
