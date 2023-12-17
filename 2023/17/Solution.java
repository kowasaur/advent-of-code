import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        Grid map = AoC.readGrid("17/input.txt");
        Point end = new Point(map.cols.length-1, map.rows.length-1);

        var nodes = new Node[map.rows.length][map.cols.length][3][4];
        for (int y = 0; y < map.rows.length; y++) {
            for (int x = 0; x < map.cols.length; x++) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        nodes[y][x][i][j] = new Node(new Point(x, y), i, Direction.values()[j], map);
                    }
                }
            }
        }

        var open = new HashSet<Node>();
        var start = nodes[0][0][0][0];
        start.g_score = 0;
        open.add(start);
        
        while (open.size() > 0) {
            Node current = open.stream().reduce((min, x) -> x.loss < min.loss ? x : min).get();
            open.remove(current);
            if (current.p.equals(end)) continue;


            for (var dir : Direction.values()) {
                var n = current.p.moved(dir);
                if (!map.inBounds(n)) continue;
                if (dir.opposite() == current.dir) continue;

                int con = 0;
                if (dir == current.dir) {
                    if (current.consecutive == 2) continue;
                    con = current.consecutive+1;
                }

                Node neighbour = nodes[n.y][n.x][con][dir.ordinal()];
                var new_g_score = current.g_score + neighbour.loss;
                if (new_g_score < neighbour.g_score) {
                    neighbour.g_score = new_g_score;
                    open.add(neighbour);
                }
                
            }
        }

        int result1 = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                result1 = Math.min(result1, nodes[end.y][end.x][i][j].loss);
            }
        }
        AoC.printResult(1, result1);
    }
}

class Node {
    public final Point p;
    public final int consecutive;
    public final Direction dir;
    public final int loss;
    public int g_score = 1000000;

    public Node(Point p, int consecutive, Direction dir, Grid map) {
        this.p = p;
        this.consecutive = consecutive;
        this.dir = dir;
        this.loss = map.get(p) - '0';
    }

    @Override
    public String toString() {
        return Integer.toString(g_score);
    }
}
