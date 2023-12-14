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
        var grid = AoC.readGrid("14/input.txt");

        var grid1 = grid.copy();
        tilt(grid1);
        AoC.printResult(1, load(grid1));

        var loads = new HashMap<Integer, ArrayList<Integer>>();
        for (int i = 0; i < 100; i++) grid = cycle(grid);
        for (int i = 100; i < 150; i++) {
            grid = cycle(grid);
            var l = load(grid);
            if (loads.containsKey(l)) loads.get(l).add(i);
            else {
                var indices = new ArrayList<Integer>();
                indices.add(i);
                loads.put(l, indices);
            }
        }

        int modulo = -1;
        for (var entry : loads.entrySet()) {
            var inds = entry.getValue();
            if (inds.size() == 3) modulo = inds.get(2) - inds.get(0);
        }

        for (var entry : loads.entrySet()) {
            if ((999999999 - entry.getValue().getLast()) % modulo == 0) {
                AoC.printResult(2, entry.getKey());
                return;
            }
        }
    }
}
