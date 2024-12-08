package mazez;

import mazez.model.Position;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MazeValidatorTest {

    MazeValidator mazeValidator = new MazeValidator();

    @Test
    public void allows_solvable_maze() {
        var random = new Random(10203929292L);
        var generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
        var mazeGenerator = new MazeGenerator(generationParams, random);
        var board = mazeGenerator.carve();

        assertTrue(mazeValidator.validate(board));
    }

    @Test
    public void does_not_allow_unsolvable_maze() {
        var random = new Random(1020392);
        var generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
        var mazeGenerator = new MazeGenerator(generationParams, random);
        var board = mazeGenerator.carve();

        assertFalse(mazeValidator.validate(board));
    }
}
