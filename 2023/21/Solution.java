import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        var grid = AoC.readGrid("21/input.txt");

        var points = new HashSet<Point>();
        points.add(grid.findFirst('S'));

        for (int i = 0; i < 64; i++) {
            var new_points = new HashSet<Point>();
            for (Point point : points) {
                for (var dir : Direction.values()) {
                    var p = point.moved(dir);
                    if (grid.inBounds(p) && grid.get(p) != '#') new_points.add(p);
                }
            }
            points = new_points;
        }

        AoC.printResult(1, points.size());
        
        int result2 = 0;

        AoC.printResult(2, result2);
    }
}
