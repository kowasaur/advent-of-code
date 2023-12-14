package utils;

import java.util.Arrays;

// This assumes it is a rectangle and the dimensions can't change
public class Grid {
    public final char[][] rows;
    public final char[][] cols;

    public Grid(String grid) {
        rows = Arrays.stream(grid.split("\n")).map(s -> s.toCharArray()).toArray(char[][]::new);
        cols = new char[rows[0].length][rows.length];
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < cols.length; x++) {
                cols[x][y] = rows[y][x];
            }
        }
    }

    public char get(int row, int column) {
        return rows[row][column];
    }

    public void set(int row, int column, char value) {
        rows[row][column] = value;
        cols[column][row] = value;
    }

    public String[] rowStrings() {
        return Arrays.stream(rows).map(c -> new String(c)).toArray(String[]::new);
    }

    public String[] colStrings() {
        return Arrays.stream(cols).map(c -> new String(c)).toArray(String[]::new);
    }

    @Override
    public String toString() {
        return String.join("\n", rowStrings());
    }
}
