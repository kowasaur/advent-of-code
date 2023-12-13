package utils;

import java.util.Arrays;
import java.util.stream.IntStream;

// This assumes it is a rectangle and it is intended to be immutable
public class Grid {
    public final String[] rows;
    public final String[] cols;

    public Grid(String grid) {
        rows = grid.split("\n");
        cols = IntStream.range(0, rows[0].length())
            .mapToObj(n -> Arrays.stream(rows).reduce("", (a,b) -> a + b.charAt(n)))
            .toArray(String[]::new);
    }

    public char get(int row, int column) {
        return rows[row].charAt(column);
    }
}
