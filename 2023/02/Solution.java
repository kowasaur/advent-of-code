import java.io.IOException;
import java.util.Arrays;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        int result1 = 0;
        int result2 = 0;

        var lines = AoC.readLines("02/input.txt");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).split(": ")[1];
            boolean possible = true;

            int min_red = 0;
            int min_green = 0;
            int min_blue = 0;

            for (String set : line.split("; ")) {
                for (String part : set.split(", ")) {
                    String[] x = part.split(" ");
                    int n = Integer.parseInt(x[0]);
                    String t = x[1];
                    if ((t.equals("red") && n > 12) || (t.equals("green") && n > 13) || (t.equals("blue") && n > 14)) {
                        possible = false;
                    }

                    if (t.equals("red"))
                        min_red = Math.max(min_red, n);
                    else if (t.equals("blue"))
                        min_blue = Math.max(min_blue, n);
                    else if (t.equals("green"))
                        min_green = Math.max(min_green, n);
                }
            }

            if (possible)
                result1 += 1 + i;

            result2 += min_red * min_blue * min_green;
        }

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
