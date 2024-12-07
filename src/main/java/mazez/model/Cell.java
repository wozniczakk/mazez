package mazez.model;

public class Cell {

    private final int row;
    private final int column;
    private boolean hasNPassage;
    private boolean hasSPassage;
    private boolean hasEPassage;
    private boolean hasWPassage;
    private boolean hasCoin;
    private boolean hasSpikeTrap;
    private boolean hasSpiderTrap;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean hasSpikeTrap() {
        return hasSpikeTrap;
    }

    public void setHasSpikeTrap(boolean hasSpikeTrap) {
        this.hasSpikeTrap = hasSpikeTrap;
    }

    public void setHasSpiderTrap(boolean hasSpiderTrap) {
        this.hasSpiderTrap = hasSpiderTrap;
    }

    public boolean hasSpiderTrap() {
        return hasSpiderTrap;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Position getPosition(){
        return new Position(row, column);
    }

    public boolean hasNPassage() {
        return hasNPassage;
    }

    public boolean hasSPassage() {
        return hasSPassage;
    }

    public boolean hasEPassage() {
        return hasEPassage;
    }

    public boolean hasCoin() {
        return hasCoin;
    }

    public boolean hasWPassage() {
        return hasWPassage;
    }

    public void setHasNPassage(boolean hasNPassage) {
        this.hasNPassage = hasNPassage;
    }

    public void setHasSPassage(boolean hasSPassage) {
        this.hasSPassage = hasSPassage;
    }

    public void setHasEPassage(boolean hasEPassage) {
        this.hasEPassage = hasEPassage;
    }

    public void setHasWPassage(boolean hasWPassage) {
        this.hasWPassage = hasWPassage;
    }

    public void setCoin(boolean hasCoin) {
        this.hasCoin = hasCoin;
    }

    public boolean canGoInDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> hasNPassage();
            case EAST -> hasEPassage();
            case SOUTH -> hasSPassage();
            case WEST -> hasWPassage();
        };
    }
}
