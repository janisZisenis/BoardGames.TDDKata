package InputGeneration.Input;

public class Input {

    private final int row;
    private final int column;

    public Input(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public boolean equals(Object o) {
        Input i = (Input)o;
        return i.row == row && i.column == column;
    }

    public int hashCode() {
        return ("row: " + row + " column: " + column).hashCode();
    }

}
