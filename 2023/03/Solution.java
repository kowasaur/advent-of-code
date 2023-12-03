import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.*;

class Solution {
    static int geer_y = -1;
    static int geer_x = -1;

    static boolean isSymbol(List<String> lines, int y, int x) {
        char c = lines.get(y).charAt(x);
        if (c == '*') {
            geer_x = x;
            geer_y = y;
        }
        return !(Character.isDigit(c) || c == '.');
    }

    public static void main(String[] _args) throws IOException {
        int result1 = 0;
        int result2 = 0;

        var lines = AoC.readLines("03/input.txt");

        int length = lines.size();
        int width = lines.get(0).length();

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[][] cogs = (ArrayList<Integer>[][]) new ArrayList[length][width];

        for (int y = 0; y < length; y++) {
            String buffer = "";

            for (int x = 0; x < width; x++) {
                boolean symbol_exists = false;
                int og_x = x;

                while (x < width && Character.isDigit(lines.get(y).charAt(x))) {
                    buffer += lines.get(y).charAt(x);

                    if ((y > 0 && isSymbol(lines, y - 1, x)) || (y < length - 1 && isSymbol(lines, y + 1, x)))
                        symbol_exists = true;

                    x++;
                }

                if (buffer.length() == 0)
                    continue;

                if (og_x > 0) {
                    if ((y > 0 && isSymbol(lines, y - 1, og_x - 1)) || isSymbol(lines, y, og_x - 1)
                            || (y < length - 1 && isSymbol(lines, y + 1, og_x - 1)))
                        symbol_exists = true;
                }
                if (x < width) {
                    if ((y > 0 && isSymbol(lines, y - 1, x)) || isSymbol(lines, y, x)
                            || (y < length - 1 && isSymbol(lines, y + 1, x)))
                        symbol_exists = true;
                }

                if (symbol_exists)
                    result1 += Integer.parseInt(buffer);

                if (geer_x != -1) {
                    if (cogs[geer_y][geer_x] == null) {
                        cogs[geer_y][geer_x] = new ArrayList<Integer>();
                    }
                    cogs[geer_y][geer_x].add(Integer.parseInt(buffer));
                    geer_x = -1;
                    geer_y = -1;
                }

                buffer = "";
            }
        }

        AoC.printResult(1, result1);

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                var parts = cogs[y][x];
                if (parts != null && parts.size() == 2)
                    result2 += parts.get(0) * parts.get(1);
            }
        }

        AoC.printResult(2, result2);
    }
}
