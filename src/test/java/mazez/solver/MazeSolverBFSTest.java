package mazez.solver;

import mazez.MazeGenerator;
import mazez.model.Position;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static mazez.Mode.COLLECT_COINS;
import static mazez.Mode.FIND_SHORTEST_PATH;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class MazeSolverBFSTest {

    @Test
    public void finds_shortest_path() {
        var out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        var random = new Random(10203929292L);
        var generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
        var mazeGenerator = new MazeGenerator(generationParams, random);
        var board = mazeGenerator.carve();

        var mazeSolverBFS = new MazeSolverBFS();
        mazeSolverBFS.printSolution(board, FIND_SHORTEST_PATH);
        var actual = out.toString().trim();
        var expectedBoard = """
                ___________________
                |  ()_____  | |     |
                |_|()()()() |___| | |
                |()()()()()_  |  _| |
                |()()     |  _| | | |
                |()()_| | |_|   |___|
                |()  ___| |_| |_|_  |
                |()_  |___| | |_|_| |
                |()()  _____|_____| |
                |_|()_______  |_|_  |
                |X_()___________|_|_|""";
        var answer = "Shortest path length is 20.";
        assertTrue(actual.contains(answer));
        assertTrue(actual.contains(expectedBoard));
    }

    @Test
    public void throws_when_no_path_to_exit() {
        var random = new Random(102039293);
        var generationParams = new MazeGenerator.GenerationParams(10, new Position(0, 0), 5);
        var mazeGenerator = new MazeGenerator(generationParams, random);
        var board = mazeGenerator.carve();

        var mazeSolverBFS = new MazeSolverBFS();
        assertThrows(IllegalStateException.class, () -> mazeSolverBFS.printSolution(board, FIND_SHORTEST_PATH));
    }

    @Test
    public void rejects_unsupported_mode() {
        var mazeSolverBFS = new MazeSolverBFS();
        assertThrows(UnsupportedOperationException.class, () -> mazeSolverBFS.printSolution(null, COLLECT_COINS));
    }
}
