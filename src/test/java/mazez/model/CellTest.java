package mazez.model;

import org.junit.jupiter.api.Test;

import static mazez.model.Direction.EAST;
import static mazez.model.Direction.NORTH;
import static mazez.model.Direction.SOUTH;
import static mazez.model.Direction.WEST;
import static org.assertj.core.api.Assertions.assertThat;

public class CellTest {

    @Test
    public void sets_passage() {
        var cell = new Cell(0, 0);
        cell.setNPassage(true);
        cell.setWPassage(true);

        assertThat(cell.hasNPassage()).isTrue();
        assertThat(cell.hasWPassage()).isTrue();
        assertThat(cell.hasSPassage()).isFalse();
        assertThat(cell.hasEPassage()).isFalse();
    }

    @Test
    public void can_go_only_in_direction_with_passage() {
        var cell = new Cell(0, 0);
        cell.setEPassage(true);
        cell.setSPassage(true);

        assertThat(cell.canGoInDirection(EAST)).isTrue();
        assertThat(cell.canGoInDirection(SOUTH)).isTrue();
        assertThat(cell.canGoInDirection(NORTH)).isFalse();
        assertThat(cell.canGoInDirection(WEST)).isFalse();
    }

    @Test
    public void sets_traps(){
        var cell = new Cell(1, 1);
        cell.setSpiderTrap(true);
        cell.setSpikeTrap(true);

        assertThat(cell.hasSpiderTrap()).isTrue();
        assertThat(cell.hasSpikeTrap()).isTrue();
    }

    @Test
    public void sets_coins(){
        var cell = new Cell(1, 1);
        cell.setCoin(true);

        assertThat(cell.hasCoin()).isTrue();
    }
}
