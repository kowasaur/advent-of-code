import java.io.IOException;
import java.util.Arrays;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("01/input.txt");

        char[] digits = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        var numbers = Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");

        int total1 = 0;
        int total2 = 0;

        for (String line : lines) {
            int first = 100000, last = 0;
            for (char d : digits) {
                int f = line.indexOf(d);
                int l = line.lastIndexOf(d);
                if (f >= 0)
                    first = Math.min(f, first);
                last = Math.max(l, last);
            }

            total1 += Integer.parseInt("" + line.charAt(first) + line.charAt(last));

            String f_value = "", l_value = "";

            for (String word : numbers) {
                int f = line.indexOf(word);
                int l = line.lastIndexOf(word);
                if (f >= 0 && f < first) {
                    first = f;
                    f_value = word;
                }
                if (l > last) {
                    last = l;
                    l_value = word;
                }
            }

            String v1, v2;
            if (f_value.equals(""))
                v1 = "" + line.charAt(first);
            else
                v1 = Integer.toString(numbers.indexOf(f_value) + 1);
            if (l_value.equals(""))
                v2 = "" + line.charAt(last);
            else
                v2 = Integer.toString(numbers.indexOf(l_value) + 1);

            total2 += Integer.parseInt(v1 + v2);
        }

        AoC.printResult(1, total1);
        AoC.printResult(2, total2);
    }
}
