import java.io.IOException;
import java.util.*;

import utils.*;

class Solution {
    static final int SIZE = 500;
    static final int SIZE2 = 40000000;

    static void fill(boolean[][] dug, Point p) {
        if (dug[p.y][p.x]) return;
        dug[p.y][p.x] = true;
        for (Direction dir : Direction.values()) {
            fill(dug, p.moved(dir));
        }
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("18/sample.txt");

        var dug = new boolean[SIZE][SIZE];
        Point p = new Point(SIZE/2, SIZE/2);
        Point p2 = new Point(SIZE2/2, SIZE2/2);

        int min_y = Integer.MAX_VALUE;

        var dug_edges = (ArrayList<Integer>[]) new ArrayList[SIZE2];
        for (int i = 0; i < dug_edges.length; i++) dug_edges[i] = new ArrayList<Integer>();

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

            Direction dir2;
            int amount2 = Integer.parseInt(parts[2].substring(2, 7), 16);

            if (parts[2].charAt(7) == '0') dir2 = Direction.RIGHT;
            else if (parts[2].charAt(7) == '1') dir2 = Direction.DOWN;
            else if (parts[2].charAt(7) == '2') dir2 = Direction.LEFT;
            else dir2 = Direction.UP;
            

            if (dir2.horizontal()) {
                p2 = p2.moved(dir2, amount2);
            } else {
                for (int i = 0; i <= amount2; i++) {
                    dug_edges[p2.y].add(p2.x);
                    p2 = p2.moved(dir2);
                }
            }

            min_y = Math.min(min_y, p2.y);
        }

        fill(dug, p.moved(Direction.RIGHT));

        int result1 = 0;
        for (int y = 0; y < dug.length; y++) {
            for (int x = 0; x < dug[0].length; x++) {
                if (dug[y][x]) result1++;
                // if (dug[y][x]) System.out.print('#');
                // else System.out.print('.');
            }
            // System.out.print('\n');
        }

        AoC.printResult(1, result1);

        long result2 = 0;

        for (ArrayList<Integer> xs : dug_edges) {
            if (xs.size() % 2 == 1) AoC.print(xs);
            else if (xs.size() != 0) result2++;
            for (int i = 0; i < xs.size(); i+=2) {
                
            }
        }

        AoC.printResult(2, result2);
    }
}
