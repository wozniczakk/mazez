package mazez;

import mazez.MazeGenerator.GenerationParams;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static mazez.MazeGenerator.DEFAULT_STARTING_POSITION;

public class UserInputReader {
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 70;
    Scanner scan = new Scanner(System.in).useDelimiter("\n");

    public GenerationParams getGenerationParams() {
        System.out.printf("Welcome to the maze solver. Please, provide size of the maze in range %s-%s.\n", MIN_SIZE, MAX_SIZE);
        int size = scan.nextInt();
        validateSize(size);
        System.out.printf("Chosen size is: %s\n", size);
        System.out.println("Please, provide how many obstacles you want. No more than a half (rounded up) of the size.");
        int numberOfObstacles = scan.nextInt();
        validateNumberOfObstacles(numberOfObstacles, size);
        System.out.println("Please, provide how many coins you want (lower or equal to the maze size).");
        int numberOfCoins = scan.nextInt();
        validateNumberOfCoins(numberOfCoins, size);
        return new GenerationParams(size, DEFAULT_STARTING_POSITION, numberOfCoins, numberOfObstacles);
    }

    public List<Mode> getModes() {
        System.out.printf("Please, select the mode for the solver. Available modes:\n%s\n", Arrays.toString(Mode.values()));
        return Arrays.stream(scan.next().split(",")).map(String::trim).map(Mode::valueOf).toList();
    }

    private void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("Size of the maze has to fit in the range %s-%s.".formatted(MIN_SIZE, MAX_SIZE));
        }
    }

    private void validateNumberOfObstacles(int obstacles, int size) {
        var max = Math.max(size / 2, 1);
        if (obstacles < 0 || obstacles > max) {
            throw new IllegalArgumentException("Number of obstacles has to be between 0-%s".formatted(max));
        }
    }

    private void validateNumberOfCoins(int coins, int size) {
        if (coins > size) {
            throw new IllegalArgumentException("Number of coins has to be lower or equal to size = %s".formatted(size));
        }
    }
}
