package mazez;

import mazez.solver.SolverSelector;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        var mazez = new Mazez(new UserInputReader(), new MazeValidator(), new SolverSelector(), new Random());
        mazez.run();
    }
}
