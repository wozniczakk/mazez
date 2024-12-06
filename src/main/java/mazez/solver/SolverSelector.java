package mazez.solver;

import mazez.model.Board;
import mazez.Mode;

import java.util.Map;

public class SolverSelector {
    Map<Mode, Solver> solvers;

    public SolverSelector() {
        var mazeSolverDFS = new MazeSolverDFS();
        var mazeSolverBFS = new MazeSolverBFS();
        this.solvers = Map.of(Mode.COLLECT_COINS, mazeSolverDFS,
                Mode.FIND_ALL_EXITS, mazeSolverDFS,
                Mode.FIND_SHORTEST_PATH, mazeSolverBFS);
    }

    public void solveMode(Board board, Mode mode) {
        if (!solvers.containsKey(mode)) {
            throw new IllegalArgumentException("The mode is not supported!");
        }
        solvers.get(mode).printSolution(board, mode);
    }
}
