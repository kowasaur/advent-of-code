import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static void tilt(Grid grid) {
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
    }

    static Grid cycle(Grid grid) {
        for (int i = 0; i < 4; i++) {
            tilt(grid);
            grid = grid.rotated();
        }
        return grid;
    }

    static int load(Grid grid) {
        int result = 0;
        for (int i = 0; i < grid.rows.length; i++) {
            for (int j = 0; j < grid.cols.length; j++) {
                if (grid.get(i,j) == 'O') result += grid.rows.length - i;
            }
        }
        return result;
    }

    public static void main(String[] _args) throws IOException {
        var grid = AoC.readGrid("14/sample.txt");

        var grid1 = grid.copy();
        tilt(grid1);
        AoC.printResult(1, load(grid1));

        Grid old_grid;
        for (int i = 0; i < 1000000000; i++) {
            old_grid = grid;
            grid = cycle(grid);
            if (grid.equals(old_grid)) break;
        }

        AoC.printResult(2, load(grid));
    }
}
