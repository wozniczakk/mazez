package mazez;

import mazez.MazeGenerator.GenerationParams;
import mazez.model.Board;
import mazez.solver.SolverSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static mazez.MazeDecorator.addCoins;
import static mazez.MazeDecorator.addTraps;
import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.COLLECT_COINS;
import static mazez.Mode.TRAPS;

public class Mazez {
    private final UserInputReader userInputReader;
    private final MazeValidator mazeValidator;
    private final SolverSelector solverSelector;
    private final Random random;

    public Mazez(UserInputReader userInputReader, MazeValidator mazeValidator, SolverSelector solverSelector, Random random) {
        this.userInputReader = userInputReader;
        this.mazeValidator = mazeValidator;
        this.solverSelector = solverSelector;
        this.random = random;
    }

    public void run() {
        GenerationParams params = userInputReader.getGenerationParams();
        Board maze = generateValidMaze(params);
        List<Mode> modes = userInputReader.getModes();
        decorateBoard(modes, userInputReader, maze);
        solve(maze, modes);
    }

    private void solve(Board maze, List<Mode> modes) {
        displayMaze(new ArrayList<>(), maze);
        modes.forEach(mode -> solverSelector.solveMode(maze, mode));
    }

    private Board generateValidMaze(GenerationParams params) {
        var mazeGenerator = new MazeGenerator(params, random);
        var maze = mazeGenerator.carve();

        while (!mazeValidator.validate(maze)) {
            mazeGenerator = new MazeGenerator(params, random);
            maze = mazeGenerator.carve();
        }
        return maze;
    }

    private void decorateBoard(List<Mode> modes, UserInputReader userInputReader, Board maze) {
        if (modes.contains(TRAPS)) {
            addTraps(maze, userInputReader.getNumberOfSpiderTraps(), userInputReader.getNumberOfSpikeTraps());
        }
        if (modes.contains(COLLECT_COINS)) {
            addCoins(maze, userInputReader.getNumberOfCoins());
        }
    }
}
