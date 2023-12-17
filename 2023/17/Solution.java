import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static Grid map;
    static Point end;

    static int value(Point p) {
        return map.get(p) - '0';
    }

    static int heatLoss(Point p, Direction dir, int dir_count, boolean[][] visited, int best_dist) {
        if (p.equals(end)) return value(p);
        if (!map.inBounds(p)) return 10000000;

        visited = Arrays.stream(visited).map(boolean[]::clone).toArray(boolean[][]::new);
        if (visited[p.y][p.x]) return 10000000;
        visited[p.y][p.x] = true;

        int dist = (end.x - p.x) + (end.y - p.y);
        if (dist - best_dist > 4) return 10000000;
        best_dist = Math.min(dist, best_dist);

        int rest = 10000000;
        var clock = dir.clockwise();
        var anti = dir.anticlockwise();

        if (dir_count < 3) rest = Math.min(rest, heatLoss(p.moved(dir), dir, dir_count+1, visited, best_dist));
        rest = Math.min(rest, heatLoss(p.moved(clock), clock, 0, visited, best_dist));
        rest = Math.min(rest, heatLoss(p.moved(anti), anti, 0, visited, best_dist));

        return value(p) + rest;
    }

    public static void main(String[] _args) throws IOException {
        map = AoC.readGrid("17/sample.txt");
        end = new Point(map.cols.length-1, map.rows.length-1);

        var visited = new boolean[map.rows.length][map.cols.length];
        int result1 = Math.min(heatLoss(new Point(0,0), Direction.RIGHT, 0, visited, 10000000), heatLoss(new Point(0,0), Direction.DOWN, 0, visited, 10000000));
        int result2 = 0;

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
