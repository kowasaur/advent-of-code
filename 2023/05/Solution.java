import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import utils.*;

class Solution {
    static List<long[]> parseMappings(String part) {
        var lines = part.split("\n");
        long[][] parsed = new long[lines.length-1][3];

        for (int i = 1; i < lines.length; i++) {
            parsed[i-1] = AoC.longArray(lines[i].split(" "));
        }

        return AoC.sorted(parsed, (a, b) -> Long.compare(a[1], b[1]));
    }

    static void newNums(String part, long[] nums) {
        var sorted = parseMappings(part);
        for (int i = 0; i < nums.length; i++) {
            long n = nums[i];
            for (long[] s : sorted) {
                if (n >= s[1] && n < s[1] + s[2]) {
                    nums[i] = s[0] + n - s[1];
                    break;
                }
            }
        }
    }

    static List<Long> newNums2(String part, List<Long> nums) {
        var sorted = parseMappings(part);
        var new_nums = new ArrayList<Long>();

        for (int i = 0; i < nums.size(); i += 2) {
            long n = nums.get(i);
            long l = nums.get(i+1);

            for (long[] s : sorted) {
                if ((s[1] <= n + l && n + l <= s[1] + s[2]) || (n <= s[1] + s[2] && s[1] + s[2] <= n + l)) {
                    if (s[1] > n) {
                        new_nums.add(n);
                        new_nums.add(s[1] - n);
                        l -= s[1] - n;
                        n += s[1] - n;
                    }
                    if (s[1] <= n) new_nums.add(s[0] + n - s[1]);
                    else new_nums.add(s[0]);                   

                    long start = Math.max(n, s[1]);
                    long end = Math.min(n+l, s[1]+s[2]);
                    long used = end - start;
                    new_nums.add(used);
                    l -= used;
                    n += used;
                }
            }
            if (l > 0) {
                new_nums.add(n);
                new_nums.add(l);
            }
        }
        return new_nums;
    }

    public static void main(String[] _args) throws IOException {
        var parts = Files.readString(Paths.get("05/input.txt")).trim().split("\n\n");

        var nums = AoC.longArray(parts[0].split(": ")[1].split(" "));
        var nums2 = Arrays.stream(nums).boxed().toList();

        for (int i = 1; i < parts.length; i++) {
            newNums(parts[i], nums);
            nums2 = newNums2(parts[i], nums2);
        }

        long result2 = Long.MAX_VALUE;
        for (int i = 0; i < nums2.size(); i += 2) {
            result2 = Math.min(result2, nums2.get(i));
        }

        AoC.printResult(1, Arrays.stream(nums).min().getAsLong());
        AoC.printResult(2, result2);
    }
}
