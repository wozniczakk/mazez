package mazez;

import mazez.model.Cell;
import mazez.model.Position;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static mazez.MazeDecorator.addCoins;
import static mazez.MazeDecorator.addTraps;
import static org.assertj.core.api.Assertions.assertThat;

public class MazeDecoratorTest {
    Random random = new Random(10203929292L);
    MazeGenerator.GenerationParams generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
    MazeGenerator mazeGenerator = new MazeGenerator(generationParams, random);

    @Test
    public void generates_coins() {
        var board = mazeGenerator.carve();
        var numberOfCoins = 5;
        addCoins(board, numberOfCoins, random);

        var expectedCellsWithCoins = List.of(
                board.getCell(3, 5),
                board.getCell(3, 6),
                board.getCell(4, 0),
                board.getCell(5, 6),
                board.getCell(8, 8)
        );
        var cellsWithCoins = board.getAllCells().stream().filter(Cell::hasCoin).toList();

        assertThat(cellsWithCoins.size()).isEqualTo(numberOfCoins);
        assertThat(cellsWithCoins).isEqualTo(expectedCellsWithCoins);
    }

    @Test
    public void generates_traps() {
        var board = mazeGenerator.carve();
        var numberOfSpiderTraps = 2;
        var numberOfSpikeTraps = 3;
        addTraps(board, numberOfSpiderTraps, numberOfSpikeTraps, random);

        var expectedCellsWithSpiderTraps = List.of(
                board.getCell(4, 0),
                board.getCell(5, 6)
        );

        var expectedCellsWithSpikeTraps = List.of(
                board.getCell(3, 5),
                board.getCell(3, 6),
                board.getCell(8, 8)
        );

        var cellsWithSpiderTraps = board.getAllCells().stream().filter(Cell::hasSpiderTrap).toList();
        var cellsWithSpikeTraps = board.getAllCells().stream().filter(Cell::hasSpikeTrap).toList();

        assertThat(cellsWithSpiderTraps.size()).isEqualTo(numberOfSpiderTraps);
        assertThat(cellsWithSpikeTraps.size()).isEqualTo(numberOfSpikeTraps);
        assertThat(expectedCellsWithSpikeTraps).isEqualTo(cellsWithSpikeTraps);
        assertThat(expectedCellsWithSpiderTraps).isEqualTo(cellsWithSpiderTraps);
    }
}
