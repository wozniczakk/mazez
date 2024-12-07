package mazez;

import mazez.solver.SolverSelector;

public class Main {
    public static void main(String[] args) {
        var mazez = new Mazez(new UserInputReader(), new MazeValidator(), new SolverSelector());
        mazez.run();
    }
}
