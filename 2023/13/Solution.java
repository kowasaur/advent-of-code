import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import utils.*;

class Solution {
    static IdentityHashMap<String, Boolean> already = new IdentityHashMap<String, Boolean>();

    static int findReflection(String[] lines) {
        for (int i = 0; i < lines.length - 1; i++) {
            var reflection = true;
            for (int j = 0; j <= Math.min(i, lines.length-i-2); j++) {
                if (!lines[i-j].equals(lines[i+j+1])) reflection = false;
            }
            if (reflection) {
                already.put(lines[i], true);
                return i+1;
            }
        }
        return -1;
    }

    static boolean nearlyEquals(String a, String b) {
        var one_wrong = false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (one_wrong) return false;
                one_wrong = true;
            }
        }
        return true;
    }

    static int findSmudgedReflection(String[] lines) {
        for (int i = 0; i < lines.length - 1; i++) {
            if (already.containsKey(lines[i])) continue;
            var reflection = true;
            var one_almost = false;
            for (int j = 0; j <= Math.min(i, lines.length-i-2); j++) {
                if (!lines[i-j].equals(lines[i+j+1])) {
                    if (one_almost || !nearlyEquals(lines[i-j], lines[i+j+1])) reflection = false;
                    else one_almost = true;
                }
            }
            if (reflection) return i+1;
        }
        return -1;
    }

    static int summarizePattern(Grid pattern, Function<String[], Integer> find) {
        int vertical = find.apply(pattern.colStrings());
        if (vertical != -1) return vertical;
        return find.apply(pattern.rowStrings()) * 100;
    }

    public static void main(String[] _args) throws IOException {
        var patterns = AoC.readGrids("13/input.txt");
        
        int result1 = Arrays.stream(patterns).mapToInt(p -> summarizePattern(p, Solution::findReflection)).sum();
        int result2 = Arrays.stream(patterns).mapToInt(p -> summarizePattern(p, Solution::findSmudgedReflection)).sum();

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
