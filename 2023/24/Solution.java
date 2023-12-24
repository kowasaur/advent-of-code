import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        var hailstones = AoC.readLines("24/input.txt").stream().map(Hailstone::new).toList();

        int result1 = 0;
        for (int i = 0; i < hailstones.size(); i++) {
            for (int j = i+1; j < hailstones.size(); j++) {
                if (hailstones.get(i).intersects(hailstones.get(j))) result1++;
            }
        }
        AoC.printResult(1, result1);

        int result2 = 0;

        AoC.printResult(2, result2);
    }
}

class Hailstone {
    long[] pos;
    long[] vel;

    static final long MIN = 200000000000000l;
    static final long MAX = 400000000000000l;

    public Hailstone(String line) {
        var parts = line.split(" @ ");
        pos = AoC.longArray(parts[0], ", ");
        vel = AoC.longArray(parts[1], ", ");
    }

    public boolean intersects(Hailstone other) {
        var m1 = (double)vel[1] / vel[0];
        var m2 = (double)other.vel[1] / other.vel[0];
        if (m1 == m2) return false;
        // if (other.vel[1] * vel[0] == vel[1] * other.vel[0]) return false;
        // var t1 = intersectTime(other, 0);
        // var t2 = intersectTime(other, 1);
        var x = (other.pos[1] - pos[1] + m1*pos[0] - m2*other.pos[0]) / (m1 - m2);
        var y = m1 * (x - pos[0]) + pos[1];

        if (x < MIN || y < MIN || x > MAX || y > MAX) return false;

        return !(inPast(x) || other.inPast(x));
    }

    double intersectTime(Hailstone other, int i) {
        return (pos[i] - other.pos[i]) / (other.vel[i] - vel[i]);
    }

    boolean inPast(double x) {
        return (vel[0] > 0 && x < pos[0] || vel[0] < 0 && x > pos[0]);
    }
}
