import java.util.stream.IntStream;
import utils.*;

class Solution {
    static int part2 = 0;

    static boolean part2Check(int n) {
        String number = Integer.toString(n);
        for (int i = 0; i < 5; i++) {
            if (number.charAt(i) == number.charAt(i+1)) {
                if (i+2 >= 6 || number.charAt(i+1) != number.charAt(i+2)) {
                    return true;
                }
                while (i+2 < 6 && number.charAt(i+1) == number.charAt(i+2)) {
                    i++;
                }
            }
        }
        return false;
    }

    public static void main(String[] _args) {
        int[] bounds = AoC.readNumbersSplitBy("-", "04/input.txt");
        int min = bounds[0];
        int max = bounds[1];

        long part1 = IntStream.rangeClosed(min, max)
            .filter(n -> {
                String number = Integer.toString(n);
                boolean dub = false;
                for (int i = 0; i < 5; i++) {
                    if (number.charAt(i) > number.charAt(i+1)) return false;
                    if (number.charAt(i) == number.charAt(i+1)) dub = true;
                }
                return dub;
            })
            .peek(n -> { if (part2Check(n)) part2++; })
            .count();

        AoC.printResult(1, part1);
        AoC.printResult(2, part2);
    }
}
