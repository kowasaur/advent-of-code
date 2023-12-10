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

    static boolean[][] pipe;
    static boolean[][] outside;
    static boolean[][] checked;

    static int loopLength(int x, int y, Direction dir, List<String> lines) {
        int length = 0;
        pipe = new boolean[lines.size()*2][lines.get(0).length()*2];

        while (true) {
            if (dir == Direction.RIGHT) {
                pipe[2*y][2*x+1] = true;
                x++;
            } else if (dir == Direction.LEFT) {
                pipe[2*y][2*x-1] = true;
                x--;
            } else if (dir == Direction.UP) {
                pipe[2*y-1][2*x] = true;
                y--;
            } else {
                pipe[2*y+1][2*x] = true;
                y++;
            }
            
            if (!inRange(x, y)) return -1;

            pipe[2*y][2*x] = true;
            length++;
            char c = lines.get(y).charAt(x);
            if (c == 'S') return length;

            if (c == 'L') dir = dir == Direction.DOWN ? Direction.RIGHT : Direction.UP;
            else if (c == 'J') dir = dir == Direction.DOWN ? Direction.LEFT : Direction.UP;
            else if (c == '7') dir = dir == Direction.UP ? Direction.LEFT : Direction.DOWN;
            else if (c == 'F') dir = dir == Direction.UP ? Direction.RIGHT : Direction.DOWN;
        }
    } 

    static boolean inRange(int x, int y) {
        return x >= 0 && x < pipe[0].length && y >= 0 && y < pipe.length;
    }

    static void findOutside(int x, int y) {
        if (!inRange(x,y) || checked[y][x]) return;
        checked[y][x] = true;
        if (pipe[y][x]) return;
        outside[y][x] = true;
        findOutside(x+1, y);
        findOutside(x-1, y);
        findOutside(x, y+1);
        findOutside(x, y-1);
    }
    
    public static void main(String[] args) throws IOException {
        var lines = AoC.readLines("10/input.txt");
        
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

        outside = new boolean[pipe.length][pipe[0].length];
        checked = new boolean[pipe.length][pipe[0].length];
        
        for (int x = 0; x < pipe[0].length; x++) findOutside(x, 0);
        
        if (args.length == 1) {
            visual();
            return;
        }

        AoC.printResult(1, result1 / 2);
        
        int result2 = lines.size() * lines.get(0).length() - result1;
        for (int y = 0; y < pipe.length; y += 2) {
            for (int x = 0; x < pipe[0].length; x += 2) {
                if (outside[y][x]) result2--;
            }
        }
        AoC.printResult(2, result2);
    }

    static void visual() {
        for (int y = 0; y < pipe.length; y++) {
            for (int x = 0; x < pipe[0].length; x++) {
                if (pipe[y][x]) System.out.print("\u001B[31m");
                else if (outside[y][x]) System.out.print("\u001B[32m");
                else {System.out.print("\u001B[0m");}
                System.out.print('█');
            }
            System.out.print('\n');
        }
    }
}
