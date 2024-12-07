package mazez;

public class Main {
    public static void main(String[] args) {
        var mazez = new Mazez(new UserInputReader(), new MazeValidator());
        mazez.run();
    }
}
