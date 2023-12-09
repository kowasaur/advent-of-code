import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import utils.*;

class Solution {
    static long nextVal(long[] nums, Function<long[], Integer> ind, int k) {
        for (long l : nums) {
            if (l == nums[0]) continue;
            long[] diffs = new long[nums.length -1];
            for (int i = 0; i < diffs.length; i++) {
                diffs[i] = nums[i+1] - nums[i];
            }
            return k*nextVal(diffs, ind, k) + nums[ind.apply(nums)];
            
        }
        return nums[0];
    }

    public static long extrapolationSum(List<String> lines,  Function<long[], Integer> ind, int k) {
        return lines.stream().map(l -> AoC.longArray(l, " ")).mapToLong(x -> nextVal(x, ind, k)).sum();
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("09/input.txt");
        AoC.printResult(1, extrapolationSum(lines, xs -> xs.length-1, 1));
        AoC.printResult(2, extrapolationSum(lines, _xs -> 0, -1));
    }
}
