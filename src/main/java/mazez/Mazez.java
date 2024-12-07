package mazez;

import mazez.MazeGenerator.GenerationParams;
import mazez.model.Board;
import mazez.solver.SolverSelector;

import java.util.ArrayList;
import java.util.List;

import static mazez.MazeDecorator.addCoins;
import static mazez.MazeDecorator.addTraps;
import static mazez.MazePrinter.displayMaze;
import static mazez.Mode.COLLECT_COINS;
import static mazez.Mode.TRAPS;

public class Mazez {
    private final UserInputReader userInputReader;
    private final MazeValidator mazeValidator;

    public Mazez(UserInputReader userInputReader, MazeValidator mazeValidator) {
        this.userInputReader = userInputReader;
        this.mazeValidator = mazeValidator;
    }

    public void run(){
        GenerationParams params = userInputReader.getGenerationParams();
        Board maze = generateValidMaze(params);
        List<Mode> modes = userInputReader.getModes();
        decorateBoard(modes, userInputReader, maze);
        solve(maze, modes);
    }

    private void solve(Board maze, List<Mode> modes) {
        SolverSelector solverSelector = new SolverSelector();
        displayMaze(new ArrayList<>(), maze);
        modes.forEach(mode -> solverSelector.solveMode(maze, mode));
    }

    private Board generateValidMaze(GenerationParams params) {
        var mazeGenerator = new MazeGenerator(params);
        var maze = mazeGenerator.carve();

        while (!mazeValidator.validate(maze)) {
            mazeGenerator = new MazeGenerator(params);
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
