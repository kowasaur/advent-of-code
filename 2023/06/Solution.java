import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static long[] parseLine(String line) {
        var strs = line.split(": +")[1].split(" +");
        var p1 = AoC.longArray(strs);
        var nums = Arrays.copyOf(p1, p1.length+1);
        nums[p1.length] = Long.parseLong(String.join("", strs));
        return nums;
    }

    static int ways(long k, long d) {
        var ends = AoC.quadratic(1, -k, d);
        return (int) (Math.ceil(ends[1]) - Math.floor(ends[0]) - 1);
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("06/input.txt");
        long[] times = parseLine(lines.get(0));
        long[] distances = parseLine(lines.get(1));

        int result1 = 1;
        for (int i = 0; i < times.length - 1; i++) result1 *= ways(times[i], distances[i]);
        int result2 = ways(times[times.length-1], distances[times.length-1]);

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
