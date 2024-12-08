package mazez;

import mazez.MazeGenerator.GenerationParams;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static mazez.MazeGenerator.DEFAULT_STARTING_POSITION;

public class UserInputReader {
    private static final int MIN_SIZE = 5;
    private static final int MAX_SIZE = 70;
    private static final int MAX_TRAPS = 5;
    Scanner scan = new Scanner(System.in).useDelimiter("\n");

    public GenerationParams getGenerationParams() {
        System.out.printf("Welcome to the maze solver. Please, provide size of the maze in range %s-%s.\n", MIN_SIZE, MAX_SIZE);
        var size = scan.nextInt();
        validateSize(size);

        System.out.println("Please, provide how many obstacles you want. No more than a half (rounded down) of the size.");
        var obstacles = scan.nextInt();
        validateNumberOfObstacles(obstacles, size);

        System.out.printf("Selected size is %s, number of obstacles is %s\n", size, obstacles);
        return new GenerationParams(size, DEFAULT_STARTING_POSITION, obstacles);
    }

    public List<Mode> getModes() {
        System.out.printf("Please, select the mode for the solver. Available modes:\n%s\n", Arrays.toString(Mode.values()));
        return Arrays.stream(scan.next().split(",")).map(String::trim).map(Mode::valueOf).toList();
    }

    public int getNumberOfCoins() {
        System.out.println("Please, provide how many coins you want on the board.");
        var coins = scan.nextInt();
        validateNumberOfCoins(coins);
        return coins;
    }

    public int getNumberOfSpiderTraps() {
        System.out.println("Please, provide how many spider traps (max 5) you want on the board (30 points penalty).");
        var spiderTraps = scan.nextInt();
        validateNumberOfTraps(spiderTraps);
        return spiderTraps;
    }

    public int getNumberOfSpikeTraps() {
        System.out.println("Please, provide how many spike traps (max 5) you want on the board (15 points penalty).");
        var spikeTraps = scan.nextInt();
        validateNumberOfTraps(spikeTraps);
        return spikeTraps;
    }

    private void validateSize(int size) {
        if (size < MIN_SIZE || size > MAX_SIZE) {
            throw new IllegalArgumentException("Size of the maze has to fit in the range %s-%s!".formatted(MIN_SIZE, MAX_SIZE));
        }
    }

    private void validateNumberOfObstacles(int obstacles, int size) {
        var max = Math.max(size / 2, 1);
        if (obstacles < 0 || obstacles > max) {
            throw new IllegalArgumentException("Number of obstacles has to be between 0-%s!".formatted(max));
        }
    }

    private void validateNumberOfCoins(int coins) {
        if (coins < 0) {
            throw new IllegalArgumentException("Number of coins must be positive!");
        }
    }

    private void validateNumberOfTraps(int traps) {
        if (traps < 0 || traps > 5) {
            throw new IllegalArgumentException("Number of traps must be positive and less than %s!".formatted(MAX_TRAPS));
        }
    }
}
