package mazez;

import mazez.MazeGenerator.GenerationParams;
import mazez.solver.SolverSelector;

import java.util.ArrayList;
import java.util.List;

import static mazez.MazeGenerator.displayMaze;

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

//        MazeSolverBFS mazeSolverBFS = new MazeSolverBFS(new int[]{0, 0}, maze.getBoard());
//        List<Cell> path = mazeSolverBFS.bfs(0, 0);
//        System.out.println("Steps to ending " + path.size());
//        mazeGenerator.displayMaze(path);
//        mazeSolverDFS.solve(maze);
//        System.out.println(mazeSolverDFS.findEndingCells().size());
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
