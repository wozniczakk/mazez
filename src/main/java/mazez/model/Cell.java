package mazez.model;

import java.util.Objects;

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

    public void setSpikeTrap(boolean hasSpikeTrap) {
        this.hasSpikeTrap = hasSpikeTrap;
    }

    public void setSpiderTrap(boolean hasSpiderTrap) {
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

    public Position getPosition() {
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

    public void setNPassage(boolean hasNPassage) {
        this.hasNPassage = hasNPassage;
    }

    public void setSPassage(boolean hasSPassage) {
        this.hasSPassage = hasSPassage;
    }

    public void setEPassage(boolean hasEPassage) {
        this.hasEPassage = hasEPassage;
    }

    public void setWPassage(boolean hasWPassage) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && column == cell.column && hasNPassage == cell.hasNPassage && hasSPassage == cell.hasSPassage && hasEPassage == cell.hasEPassage && hasWPassage == cell.hasWPassage && hasCoin == cell.hasCoin && hasSpikeTrap == cell.hasSpikeTrap && hasSpiderTrap == cell.hasSpiderTrap;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, hasNPassage, hasSPassage, hasEPassage, hasWPassage, hasCoin, hasSpikeTrap, hasSpiderTrap);
    }
}
