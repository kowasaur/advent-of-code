import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static ArrayList<Integer> xs = new ArrayList<Integer>();
    static ArrayList<Integer> ys = new ArrayList<Integer>();
    static ArrayList<Integer> expanded_rows = new ArrayList<Integer>();
    static ArrayList<Integer> expanded_cols = new ArrayList<Integer>();

    static long dist(int a, int b, ArrayList<Integer> expanded, long exp) {
        int start = Math.min(a, b);
        int end = Math.max(a, b);

        long result = 0;
        for (int k = start; k < end; k++) {
            if (expanded.contains(k)) result += exp;
            else result++;
        }
        return result;
    }

    static long lengthsSum(int exp) {
        long result = 0;
        for (int i = 0; i < xs.size(); i++) {
            for (int j = i+1; j < xs.size(); j++) {
                result += dist(xs.get(i), xs.get(j), expanded_cols, exp);
                result += dist(ys.get(i), ys.get(j), expanded_rows, exp);
            }
        }
        return result;
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("11/input.txt");

        for (int x = 0; x < lines.get(0).length(); x++) {
            var no_galaxies = true;
            for (int y = 0; y < lines.size(); y++) {
                if (lines.get(y).charAt(x) == '#') {
                    no_galaxies = false;
                    xs.add(x);
                    ys.add(y);
                }
            }
            if (no_galaxies) expanded_cols.add(x);
        }

        for (int y = 0; y < lines.size(); y++) {
            if (!lines.get(y).contains("#")) expanded_rows.add(y);
        }

        AoC.printResult(1, lengthsSum(2));
        AoC.printResult(2, lengthsSum(1000000));
    }
}
