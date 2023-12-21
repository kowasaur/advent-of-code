package utils;

import java.util.Arrays;

// This assumes it is a rectangle and the dimensions can't change
public class Grid {
    public final char[][] rows;
    public final char[][] cols;

    public Grid(String grid) {
        this(Arrays.stream(grid.split("\n")).map(s -> s.toCharArray()).toArray(char[][]::new));
    }

    public Grid(char[][] rows) {
        this.rows = rows;
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

    public char get(Point point) {
        return get(point.y, point.x);
    }

    public void set(int row, int column, char value) {
        rows[row][column] = value;
        cols[column][row] = value;
    }

    public void set(Point point, char value) {
        set(point.y, point.x, value);
    }

    public Point findFirst(char c) {
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < cols.length; x++) {
                if (rows[y][x] == c) return new Point(x, y);
            }
        }
        return null;
    }

    public boolean inBounds(Point point) {
        int x = point.x;
        int y = point.y;
        return x >= 0 && y >= 0 && y < rows.length && x < cols.length;
    }

    public Grid copy() {
        return new Grid(toString());
    }

    public Grid rotated() {
        var new_rows = new char[cols.length][rows.length];
        for (int y = 0; y < rows.length; y++) {
            for (int x = 0; x < cols.length; x++) {
                new_rows[x][rows.length-1-y] = get(y,x);
            }
        }
        return new Grid(new_rows);
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

    @Override
    public boolean equals(Object obj) {
        var other_rows = ((Grid)obj).rows;
        return Arrays.deepEquals(rows, other_rows);
    }
}
