package mazez.solver;

import mazez.Mode;
import mazez.model.Board;

import java.util.Map;

import static mazez.Mode.FIND_ALL_EXITS;
import static mazez.Mode.FIND_SHORTEST_PATH;
import static mazez.Mode.FIND_SHORTEST_PATH_WITH_WALL_JUMP;
import static mazez.Mode.TRAPS;

public class SolverSelector {
    private final Map<Mode, Solver> solvers;

    public SolverSelector() {
        var mazeSolverDFS = new MazeSolverDFS();
        var mazeSolverBFS = new MazeSolverBFS();
        var mazeSolverDijkstra = new MazeSolverDijkstra();
        var mazeSolverBellmanFord = new MazeSolverBellmanFord();
        this.solvers = Map.of(Mode.COLLECT_COINS, mazeSolverDFS,
                FIND_ALL_EXITS, mazeSolverDFS,
                FIND_SHORTEST_PATH, mazeSolverBFS,
                FIND_SHORTEST_PATH_WITH_WALL_JUMP, mazeSolverDijkstra,
                TRAPS, mazeSolverBellmanFord);
    }

    public void solveMode(Board board, Mode mode) {
        if (!solvers.containsKey(mode)) {
            throw new IllegalArgumentException("The mode is not supported!");
        }
        solvers.get(mode).printSolution(board, mode);
    }
}
