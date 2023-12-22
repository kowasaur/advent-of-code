import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static Set<Brick> wouldFall(Set<Brick> moved, Brick brick) {
        if (brick.dependents.size() == 0) return moved;

        for (var dependent : brick.dependents) {
            if (dependent.dependencies.stream().allMatch(d -> moved.contains(d))) {
                moved.add(dependent);
                wouldFall(moved, dependent);
            }
        }
        
        return moved;
    }

    static int wouldFall(Brick brick) {
        var moved = new HashSet<Brick>();
        moved.add(brick);
        return wouldFall(moved, brick).size()-1;
    }

    public static void main(String[] _args) throws IOException {
        var all_bricks = AoC.readLines("22/input.txt").stream().map(Brick::new).toList();
        @SuppressWarnings("unchecked")
        var bricks = (ArrayList<Brick>[]) new ArrayList[Brick.max_z + 1];
        for (int i = 0; i < bricks.length; i++) bricks[i] = new ArrayList<Brick>();
        for (var b : all_bricks) bricks[b.getZ()].add(b);

        for (int j = 1; j < bricks.length; j++) {
            for (int z = j; z > 0; z--) {
                var new_brick_layer = new ArrayList<Brick>();
                for (var b : bricks[z]) {
                    int fall_amount = b.z.y-b.z.x+1;
                    if (z-fall_amount < 0) {
                        new_brick_layer.add(b);
                        continue;
                    }
                    var fall = true;
                    for (var below : bricks[z-fall_amount]) {
                        if (b.onTop(below)) {
                            fall = false;
                            b.dependencies.add(below);
                            below.dependents.add(b);
                        }
                    }
                    if (fall) {
                        bricks[z-1].add(b);
                        b.fall();
                    } else {
                        new_brick_layer.add(b);
                    }
                }
                bricks[z] = new_brick_layer;
            }
        }

        int result1 = 0;
        for (var brick : all_bricks) {
            var disintegratable = true;
            for (var dependent : brick.dependents) {
                if (dependent.dependencies.size() == 1) disintegratable = false;
            }
            if (disintegratable) result1++;
        }
        AoC.printResult(1, result1);

        AoC.printResult(2, all_bricks.stream().mapToInt(Solution::wouldFall).sum());
    }
}

class Brick {
    // These should be viewed as ranges, not points
    Point x;
    Point y;
    Point z;

    Set<Brick> dependents = new HashSet<Brick>();
    Set<Brick> dependencies = new HashSet<Brick>();

    static int max_z = 0;

    public Brick(String line) {
        var ends = line.split("~");
        var start = AoC.intArray(ends[0].split(","));
        var end = AoC.intArray(ends[1].split(","));
        x = new Point(start[0], end[0]);
        y = new Point(start[1], end[1]);
        z = new Point(start[2]-1, end[2]-1);
        max_z = Math.max(max_z, getZ());
    }

    public int getZ() {
        return z.y;
    }

    public void fall() {
        z.x--;
        z.y--;
    }

    public boolean xBrick() {
        return x.x != x.y;
    }

    public boolean yBrick() {
        return y.x != y.y;
    }

    public boolean zBrick() {
        return z.x != z.y;
    }

    public boolean onTop(Brick other) {
        if (zBrick()) {
            if (other.zBrick()) return x.x == other.x.x && y.x == other.y.x;
            if (other.yBrick()) return x.x == other.x.x && contains(other.y, y.x);
            return y.x == other.y.x && contains(other.x, x.x);
        }
        if (yBrick()) {
            if (other.xBrick()) return contains(y, other.y.x) && contains(other.x, x.x);
            if (x.x != other.x.x) return false;
            return contains(y, other.y);
        }
        if (other.yBrick()) return contains(other.y, y.x) && contains(x, other.x.x);
        if (y.x != other.y.x) return false;
        return contains(x, other.x);
    }

    static boolean contains(Point r, int p) {
        return r.x <= p && p <= r.y;
    }

    static boolean contains(Point r1, Point r2) {
        return contains(r1, r2.x) || contains(r1, r2.y) || contains(r2, r1.x);
    }

    @Override
    public String toString() {
        return x.x + "," + y.x + "," + z.x + "~" + x.y + "," + y.y + "," + z.y;
    }
}
