package mazez;

import mazez.model.Board;
import mazez.model.Cell;
import mazez.model.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MazeGeneratorTest {

    Random random = new Random(10203929292L);
    MazeGenerator.GenerationParams generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
    MazeGenerator mazeGenerator = new MazeGenerator(generationParams, random);

    @Test
    public void test() {
        var board = mazeGenerator.carve();
        MazePrinter.displayMaze(new ArrayList<>(), board);
        for (Cell cell : board.getAllCells()) {
            if (!cell.hasSPassage() && !cell.hasEPassage() && !cell.hasWPassage() && !cell.hasNPassage())
                System.out.println(cell.getPosition());
        }

        assertEquals(board.getBoard().length, 10);
        assertEquals(board.getBoard()[0].length, 10);
        assertEquals(board.getNumberOfColumns(), 10);
        assertEquals(board.getNumberOfRows(), 10);
        assertEquals(board.getStartingPosition(), new Position(0, 0));
    }

    @Test
    public void generates_obstacles() {
        var board = mazeGenerator.carve();
        var obstacles = getObstaclesFrom(board);
        var expectedObstacles = Set.of(
                board.getCell(0, 8),
                board.getCell(3, 8),
                board.getCell(4, 5),
                board.getCell(3, 7),
                board.getCell(1, 7));

        assertEquals(expectedObstacles, obstacles);
    }

    private Set<Cell> getObstaclesFrom(Board board) {
        return board.getAllCells().stream()
                .filter(cell -> !cell.hasSPassage() && !cell.hasEPassage() && !cell.hasWPassage() && !cell.hasNPassage())
                .collect(toSet());
    }
}
