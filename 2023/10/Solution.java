import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    static List<String> lines;
    static boolean[][] pipe;
    static boolean[][] outside;
    static boolean[][] checked;
    static int max_x;
    static int max_y;

    static int loopLength(int x, int y, Direction dir, List<String> lines) {
        int length = 0;

        pipe = new boolean[lines.size()][max_x];

        while (true) {
            if (dir == Direction.RIGHT) x++;
            else if (dir == Direction.LEFT) x--;
            else if (dir == Direction.UP) y--;
            else y++;
            
            if (!inRange(x, y)) return -1;

            pipe[y][x] = true;
            length++;
            char c = lines.get(y).charAt(x);
            if (c == 'S') return length;

            if (c == 'L') dir = dir == Direction.DOWN ? Direction.RIGHT : Direction.UP;
            else if (c == 'J') dir = dir == Direction.DOWN ? Direction.LEFT : Direction.UP;
            else if (c == '7') dir = dir == Direction.UP ? Direction.LEFT : Direction.DOWN;
            else if (c == 'F') dir = dir == Direction.UP ? Direction.RIGHT : Direction.DOWN;
        }
    } 

    static boolean leftVertical(char c) {
        return c == '|' || c == 'J' || c == '7';
    }

    static boolean rightVertical(char c) {
        return c == '|' || c == 'L' || c == 'F';
    }

    static boolean canVertical(int x, int y) {
        var line = lines.get(y);
        return inRange(x+1,y) && pipe[y][x+1] && leftVertical(line.charAt(x)) && rightVertical(line.charAt(x+1));
    }

    static boolean canHorizontal(int x, int y) {
        return inRange(x,y+1) && pipe[y+1][x] && topHorizontal(lines.get(y).charAt(x)) && bottomHorizontal(lines.get(y+1).charAt(x));
    }

    static boolean topHorizontal(char c) {
        return c == '-' || c == 'L' || c == 'J';
    }

    static boolean bottomHorizontal(char c) {
        return c == '-' || c == 'F' || c == '7';
    }

    static boolean inRange(int x, int y) {
        return x >= 0 && x < max_x && y >= 0 && y < max_y;
    }

    static int numOutside(int x, int y) {
        if (!inRange(x, y) || checked[y][x]) return 0;
        checked[y][x] = true;
        int result = 0;

        if (pipe[y][x]) {
            if (canVertical(x, y)) {
                System.out.printf("%d %d\n", x, y);
                result += middleVert(x, y+1) + middleVert(x, y-1);}
            if (canHorizontal(x, y)) result += middleHor(x+1,y) + middleHor(x-1,y);
            return result;
        }

        outside[y][x] = true;

        for (int ny = y-1; ny <= y+1; ny++) {
            for (int nx = x-1; nx <= x+1; nx++) {
                result += numOutside(nx, ny);
            }
        }
        return result + 1;
    }

    // assuming no "complex" middle stuff
    static int middleVert(int x, int y) {
        if (!inRange(x, y) || checked[y][x]) return 0;
        if (!pipe[y][x]) return numOutside(x, y);
        if (!pipe[y][x+1]) return numOutside(x+1, y);
        if (!canVertical(x, y)) return 0;
        checked[y][x] = true;
        return middleVert(x, y+1) + middleVert(x, y-1);
    }

    static int middleHor(int x, int y) {
        if (!inRange(x, y) || checked[y][x]) return 0;
        if (!pipe[y][x]) return numOutside(x, y);
        if (!pipe[y+1][x]) return numOutside(x, y+1);
        if (!canHorizontal(x, y)) return 0;
        checked[y][x] = true;
        return middleHor(x+1,y) + middleHor(x-1,y);
    }

    public static void main(String[] _args) throws IOException {
        lines = AoC.readLines("10/sample.txt");
        outside = new boolean[lines.size()][lines.get(0).length()];
        checked = new boolean[lines.size()][lines.get(0).length()];
        max_y = lines.size();
        max_x = lines.get(0).length();

        int start_y = 0;
        int start_x = 0;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (lines.get(y).charAt(x) == 'S') {
                    start_y = y;
                    start_x = x;
                    break;
                }
            }
        }

        int result1 = 0;
        for (Direction direction : Direction.values()) {
            result1 = loopLength(start_x, start_y, direction, lines);
            if (result1 != -1) break;
        }

        AoC.printResult(1, result1 / 2);

        int result2 = max_x * max_y - result1;
        for (int x = 0; x < max_x; x++) result2 -= numOutside(x, 0);

        int check = 0;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (pipe[y][x] && outside[y][x]) System.out.println("BROKEN");
                if (pipe[y][x]) System.out.print("\u001B[31m");
                else if (outside[y][x]) System.out.print("\u001B[32m");
                else {System.out.print("\u001B[0m");check++;}
                System.out.print(lines.get(y).charAt(x));
            }
            System.out.print('\n');
        }


        AoC.printResult(2, result2);
        System.out.println(check);
    }
}


