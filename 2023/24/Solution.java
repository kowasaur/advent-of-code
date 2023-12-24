import java.io.IOException;
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

        System.out.println("For Part 2, run the following in Mathematica: ");
        System.out.print("sol = Solve[");
        for (int i = 0; i < 3; i++) {
            var h = hailstones.get(i);
            char c = (char)('m'+ i);
            System.out.print("x+a*" + c +"==" + h.pos[0]+"+(" +h.vel[0]+")*"+ c +"&&");
            System.out.print("y+b*" + c +"==" + h.pos[1]+"+(" +h.vel[1]+")*"+ c +"&&");
            System.out.print("z+c*" + c +"==" + h.pos[2]+"+(" +h.vel[2]+")*"+ c);
            if (i != 2) System.out.print("&&");
        }
        System.out.println(", {x,y,z,a,b,c,m,n,o}]");
        System.out.println("x + y + z /. sol[[1]]");
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

        var x = (other.pos[1] - pos[1] + m1*pos[0] - m2*other.pos[0]) / (m1 - m2);
        var y = m1 * (x - pos[0]) + pos[1];

        if (x < MIN || y < MIN || x > MAX || y > MAX) return false;
        return !(inPast(x) || other.inPast(x));
    }

    boolean inPast(double x) {
        return (vel[0] > 0 && x < pos[0] || vel[0] < 0 && x > pos[0]);
    }
}
