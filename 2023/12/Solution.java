import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static String reduce(String springs, int[] groups) {
        String reduced = "";
        if (reduced.equals(springs)) return springs;
        return reduce(reduced, groups);
    }

    static boolean validArrangment(String springs, int[] groups) {
        // System.out.println(springs);
        int i = springs.indexOf("#");
        int count = 0;
        for (int amount : groups) {
            if (i == -1) return false;
            while (i < springs.length() && springs.charAt(i) == '#') {
                count++;
                i++;
            }
            if (amount != count) return false;
            i = springs.indexOf("#", i);
            count = 0;
        }
        return i == -1;
    }

    static long arrangements(String springs, int[] groups) {
        // System.out.println(line);
        long result = 0;

        var unknowns = new ArrayList<Integer>();
        for (int i = 0; i < springs.length(); i++) {
            if (springs.charAt(i) == '?') unknowns.add(i);
        }

        for (long i = 0; i < Math.pow(2, unknowns.size()); i++) {
            String subbed = "";
            int u = 0;  // unknown count
            for (int j = 0; j < springs.length(); j++) {
                char c = springs.charAt(j);
                if (c == '?') {
                    if ((i & (1 << u)) > 0) subbed += '#';
                    else subbed += '.';
                    u++;
                }
                else subbed += c;
            }
            if (validArrangment(subbed, groups)) result++;
        }
// System.out.println(result);
        return result;
    }

    static long arrangements(String line) {
        var parts = line.split(" ");
        return arrangements(parts[0], AoC.intArray(parts[1].split(",")));
    }

    static long unfoldedArrangements(String line) {
        var parts = line.split(" ");
        var springs = parts[0];
        var groups = parts[1];
        for (int i = 0; i < 4; i++) {
            springs += '?' + parts[0];
            groups += ',' + parts[1];
        }
        return arrangements(springs, AoC.intArray(groups.split(",")));
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("12/input.txt");

        // arrangements("?###???????? 3,2,1");

        long result1 = lines.stream().mapToLong(Solution::arrangements).sum();
        AoC.printResult(1, result1);
        long result2 = lines.stream().mapToLong(Solution::unfoldedArrangements).sum();
        AoC.printResult(2, result2);
    }
}
