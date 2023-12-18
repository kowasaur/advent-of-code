import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static final int SIZE = 500;

    static void fill(boolean[][] dug, Point p) {
        if (dug[p.y][p.x]) return;
        dug[p.y][p.x] = true;
        for (Direction dir : Direction.values()) {
            fill(dug, p.moved(dir));
        }
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("18/input.txt");

        var dug = new boolean[SIZE][SIZE];
        Point p = new Point(SIZE/2, SIZE/2);

        for (String line : lines) {
            var parts = line.split(" ");
            Direction dir;
            int amount = Integer.parseInt(parts[1]);

            if (parts[0].equals("R")) dir = Direction.RIGHT;
            else if (parts[0].equals("L")) dir = Direction.LEFT;
            else if (parts[0].equals("U")) dir = Direction.UP;
            else dir = Direction.DOWN;

            for (int i = 0; i < amount; i++) {
                p = p.moved(dir);
                dug[p.y][p.x] = true;
            }
        }

        fill(dug, p.moved(Direction.RIGHT));

        int result1 = 0;
        for (int y = 0; y < dug.length; y++) {
            for (int x = 0; x < dug[0].length; x++) {
                if (dug[y][x]) result1++;
            }
        }


        int result2 = 0;

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
