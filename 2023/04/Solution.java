import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        int result1 = 0;
        int result2 = 0;

        var lines = AoC.readLines("04/input.txt");
        var copies = new int[lines.size()];
        Arrays.fill(copies, 1);

        for (int i = 0; i < lines.size(); i++) {
            String p1 = lines.get(i).split(": +")[1];
            var parts = p1.split(" +\\| +");
            var winners = AoC.intArray(parts[0].split(" +"));
            var have = AoC.intArray(parts[1].split(" +"));
            int matches = 0;
            for (int h : have) {
                if (IntStream.of(winners).anyMatch(x -> x == h)) matches++;
            }
            if (matches > 0) result1 += Math.pow(2, matches-1);

            for (int j = 0; j < matches; j++) {
                copies[i+j+1] += copies[i];
            }

            result2 += copies[i];
        }

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
