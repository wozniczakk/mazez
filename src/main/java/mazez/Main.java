package mazez;

import mazez.MazeGenerator.GenerationParams;
import mazez.model.Board;
import mazez.solver.SolverSelector;

import java.util.ArrayList;
import java.util.List;

import static mazez.MazePrinter.displayMaze;

public class Main {
    public static void main(String[] args) {
        UserInputReader userInputReader = new UserInputReader();
        GenerationParams params = userInputReader.getGenerationParams();
        List<Mode> modes = userInputReader.getModes();
        solve(params, modes);
    }

    private static void solve(GenerationParams params, List<Mode> modes) {
        SolverSelector solverSelector = new SolverSelector();
        Board maze = generateValidMaze(params);
        displayMaze(new ArrayList<>(), maze);

        modes.forEach(mode -> solverSelector.solveMode(maze, mode));
    }

    private static Board generateValidMaze(GenerationParams params) {
        var mazeValidator = new MazeValidator();
        var mazeGenerator = new MazeGenerator(params);
        var maze = mazeGenerator.carve();

        while (!mazeValidator.validate(maze)) {
            mazeGenerator = new MazeGenerator(params);
            maze = mazeGenerator.carve();
        }
        return maze;
    }
}
