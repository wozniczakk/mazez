package mazez;

import mazez.model.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static mazez.MazePrinter.displayMaze;
import static org.assertj.core.api.Assertions.assertThat;


public class MazeValidatorTest {

    MazeValidator mazeValidator = new MazeValidator();

    @Test
    public void allows_solvable_maze() {
        var random = new Random(10203929292L);
        var generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
        var mazeGenerator = new MazeGenerator(generationParams, random);
        var board = mazeGenerator.carve();

        assertThat(mazeValidator.validate(board)).isTrue();
    }

    @Test
    public void does_not_allow_unsolvable_maze() {
        var random = new Random(1020392);
        var generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
        var mazeGenerator = new MazeGenerator(generationParams, random);
        var board = mazeGenerator.carve();

        displayMaze(new ArrayList<>(), board);

        assertThat(mazeValidator.validate(board)).isFalse();
    }
}
