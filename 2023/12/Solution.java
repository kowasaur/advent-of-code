import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
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

    static int arrangements(String line) {
        // System.out.println(line);
        int result = 0;

        var parts = line.split(" ");
        var springs = parts[0];
        var groups = AoC.intArray(parts[1].split(","));

        var unknowns = new ArrayList<Integer>();
        for (int i = 0; i < springs.length(); i++) {
            if (springs.charAt(i) == '?') unknowns.add(i);
        }

        for (int i = 0; i < Math.pow(2, unknowns.size()); i++) {
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

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("12/input.txt");

        // arrangements("?###???????? 3,2,1");

        int result1 = lines.stream().mapToInt(Solution::arrangements).sum();
        int result2 = 0;

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
