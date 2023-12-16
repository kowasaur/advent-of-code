import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static Grid contraption;
    static int[][] energized;

    static void move(Point p, Direction dir) {
        p = p.moved(dir);
        if (!contraption.inBounds(p)) return;
        if ((energized[p.y][p.x] & (1 << dir.ordinal())) > 0) return;

        energized[p.y][p.x] |= 1 << dir.ordinal();
        char c = contraption.get(p);

        if (c == '.' || (c == '-' && dir.horizontal()) || (c == '|' && dir.vertical())) {
            move(p, dir);
        } else if (c == '/') {
            if (dir.horizontal()) move(p, dir.anticlockwise());
            else move(p, dir.clockwise());
        } else if (c == '\\') {
            if (dir.horizontal()) move(p, dir.clockwise());
            else move(p, dir.anticlockwise());
        } else {
            if (dir.horizontal()) {
                move(p, Direction.UP);
                move(p, Direction.DOWN);
            } else {
                move(p, Direction.LEFT);
                move(p, Direction.RIGHT);
            }
        }
    }

    static int amountEnergized(int start_x, int start_y, Direction start_dir) {
        energized = new int[contraption.rows.length][contraption.cols.length];
        move(new Point(start_x, start_y), start_dir);
        return Arrays.stream(energized).mapToInt(r -> Arrays.stream(r).map(c -> (c > 0 ? 1 : 0)).sum()).sum();
    }

    public static void main(String[] _args) throws IOException {
        contraption = AoC.readGrid("16/input.txt");
        AoC.printResult(1, amountEnergized(-1, 0, Direction.RIGHT));

        int most = 0;
        for (int y = 0; y < contraption.rows.length; y++) {
            most = Math.max(most, amountEnergized(-1, y, Direction.RIGHT));
            most = Math.max(most, amountEnergized(energized[0].length, y, Direction.LEFT));
        }
        for (int x = 0; x < contraption.cols.length; x++) {
            most = Math.max(most, amountEnergized(x, -1, Direction.DOWN));
            most = Math.max(most, amountEnergized(x, energized.length, Direction.UP));
        }
        AoC.printResult(2, most);
    }
}
