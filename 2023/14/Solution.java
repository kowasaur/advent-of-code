import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        var grid = AoC.readGrid("14/input.txt");

        for (int k = grid.rows.length; k > 0; k--) {
            for (int i = 1; i < k; i++) {
                for (int j = 0; j < grid.rows[i].length; j++) {
                    if (grid.get(i,j) == 'O' && grid.get(i-1,j) == '.') {
                        grid.set(i,j,'.');
                        grid.set(i-1,j,'O');
                    }
                }
            }
        }

        int result1 = 0;
        int result2 = 0;

        for (int i = 0; i < grid.rows.length; i++) {
            for (int j = 0; j < grid.cols.length; j++) {
                if (grid.get(i,j) == 'O') result1 += grid.rows.length - i;
            }
        }

        AoC.printResult(1, result1);
        AoC.printResult(2, result2);
    }
}
