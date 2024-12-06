package mazez;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        MazeGenerator mazeGenerator = new MazeGenerator(
                new MazeGenerator.GenerationParams(20, new int[]{0, 0}, 5, 0));
        Cell[][] maze = mazeGenerator.carve();
        // extract printer and accept maze as a param

        MazeSolverDFS mazeSolverDFS = new MazeSolverDFS(new int[]{0, 0}, maze);
        while (mazeSolverDFS.findEndingCells().isEmpty()) {
            mazeGenerator = new MazeGenerator(
                    new MazeGenerator.GenerationParams(20, new int[]{0, 0}, 5, 0));
            maze = mazeGenerator.carve();
            mazeSolverDFS = new MazeSolverDFS(new int[]{0, 0}, maze);
        }

        MazeSolverBFS mazeSolverBFS = new MazeSolverBFS(new int[]{0, 0}, maze);
        List<Cell> path = mazeSolverBFS.bfs(0, 0);
        System.out.println("Steps to ending " + path.size());
        mazeGenerator.displayMaze(path);
        mazeSolverDFS.solve();
        System.out.println(mazeSolverDFS.findEndingCells().size());
    }
}
