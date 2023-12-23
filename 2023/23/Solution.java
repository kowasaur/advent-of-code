import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static Grid grid;

    static Point end;

    static ArrayList<Point> longestPath(Point p, ArrayList<Point> path) {
        path.add(p);
        if (p.equals(end)) return path;

        ArrayList<ArrayList<Point>> paths = new ArrayList<ArrayList<Point>>();

        for (var dir : Direction.values()) {
            var next = p.moved(dir);
            if (path.contains(next) || !grid.inBounds(next)) continue;
            char c = grid.get(next);
            if (c == '#') continue;
            if (c == '>' && dir != Direction.RIGHT) continue;
            if (c == '<' && dir != Direction.LEFT) continue;
            if (c == 'v' && dir != Direction.DOWN) continue;
            paths.add(longestPath(next, new ArrayList<Point>(path)));
        }

        var maybe = paths.stream().filter(pa -> pa!=null).max((p1, p2) -> Integer.compare(p1.size(), p2.size()));
        if(maybe.isEmpty()) return null;
        return maybe.get();
    }

    public static void main(String[] _args) throws IOException {
        grid = AoC.readGrid("23/input.txt");
        end = new Point(grid.cols.length - 2, grid.rows.length - 1);

        AoC.printResult(1, longestPath(new Point(1, 0), new ArrayList<Point>()).size()-1);

        AoC.printResult(2, 0);
    }
}
