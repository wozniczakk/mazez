package mazez;

public class Cell {

    private final int x;
    private final int y;
    private boolean hasNPassage;
    private boolean hasSPassage;
    private boolean hasEPassage;
    private boolean hasWPassage;
    private boolean hasCoin;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", hasNPassage=" + hasNPassage +
                ", hasSPassage=" + hasSPassage +
                ", hasEPassage=" + hasEPassage +
                ", hasWPassage=" + hasWPassage +
                ", hasCoin=" + hasCoin +
                '}';
    }
}
