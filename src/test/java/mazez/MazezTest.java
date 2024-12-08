package mazez;

import mazez.solver.SolverSelector;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MazezTest {

    UserInputReader userInputReader;
    MazeValidator mazeValidator = new MazeValidator();
    SolverSelector solverSelector = new SolverSelector();

    @Test
    public void solves_maze() {
        var random = new Random(10203929292L);
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "10\n2\nCOLLECT_COINS\n6";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        userInputReader = new UserInputReader();
        var mazez = new Mazez(userInputReader, mazeValidator, solverSelector, random);

        mazez.run();

        assertThat(out.toString()).contains("Coins found 4");
        assertThat(out.toString()).contains("Selected size is 10, number of obstacles is 2");
        assertThat(out.toString()).contains("""
                ___________________
                |    _____ *| |     |
                |_|_____  | |___|*| |
                |  _______|_  |  _|*|
                |_  |  * *|  _| | | |
                |  _|_| | | |   |___|
                | |  ___| | | |_|_  |
                | |_  |___| | |_|_| |
                |   |* _____|_   _| |
                |_| |_______  |_|   |
                |X__|_____________|_|""");
    }

    @Test
    public void generates_valid_maze() {
        //the first maze in this seed will be unsolvable
        var random = new Random(1020392);
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        var generationParamsInput = "10\n5\nTRAPS\n1\n2";
        var in = new ByteArrayInputStream(generationParamsInput.getBytes());
        System.setIn(in);

        userInputReader = new UserInputReader();
        var mazez = new Mazez(userInputReader, mazeValidator, solverSelector, random);

        mazez.run();

        assertThat(out.toString()).contains("Selected size is 10, number of obstacles is 5");
        assertThat(out.toString()).contains("Shortest path length is 28");
        assertThat(out.toString()).contains("Best score is -30");
    }
}
