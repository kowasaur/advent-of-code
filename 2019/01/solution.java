import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.ToIntFunction;

class Solution {
    static void answer(int part, List<String> lines, ToIntFunction<Integer> fuel_func) {
        int result = lines.stream().mapToInt(l -> fuel_func.applyAsInt(Integer.parseInt(l))).sum();
        System.out.printf("Part %d: %d\n", part, result);
    }

    static int fuelAmount(int n) {
        int x = n / 3  - 2;
        if (x <= 0) return 0;
        return x + fuelAmount(x); 
    }

    public static void main(String[] _args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        answer(1, lines, n -> n / 3 - 2);
        answer(2, lines, Solution::fuelAmount);
    }
}
