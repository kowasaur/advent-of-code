import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import utils.*;

class Solution {
    static HashMap<String, String[]> network = new HashMap<String, String[]>();
    static String instructions;

    static long numSteps(String node, Function<String, Boolean> endFunc) {
        long steps = 0;
        int len = instructions.length();

        for (int i = 0; i < len; i = (i + 1) % len) {
            if (endFunc.apply(node)) return steps;
            steps++;
            var instr = instructions.charAt(i);
            var next = network.get(node);
            if (instr == 'L') node = next[0];
            else node = next[1];
        }

        return -1;
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("08/input.txt");

        instructions = lines.get(0);
        network = new HashMap<String, String[]>();

        for (int i = 2; i < lines.size(); i++) {
            var l = lines.get(i);
            var key = l.substring(0, 3);
            var v1 = l.substring(7, 10);
            var v2 = l.substring(12, 15);
            network.put(key, new String[] {v1, v2});
        }

        AoC.printResult(1, numSteps("AAA", n -> n.equals("ZZZ")));

        var nodes = network.keySet().stream().filter(n -> n.charAt(2) == 'A');
        long part2 = AoC.lcm(nodes.mapToLong(n -> numSteps(n, m -> m.charAt(2) == 'Z')));
        AoC.printResult(2, part2);
    }
}
