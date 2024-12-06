package mazez;

public enum Direction {

    NORTH(new int[]{1, 0}),
    SOUTH(new int[]{-1, 0}),
    WEST(new int[]{0, -1}),
    EAST(new int[]{0, 1});

    final int[] vector;

    Direction(int[] vector) {
        this.vector = vector;
    }

    public static Direction getOpposite(Direction direction) {
        return switch (direction) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }
}