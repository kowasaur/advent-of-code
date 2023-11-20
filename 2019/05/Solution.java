import java.io.IOException;
import utils.*;

class Solution {
    static int opcode(int[] program, int i) {
        return program[i] % 100;
    }

    static int value(int[] program, int i, int offset) {
        int mode = program[i] / (int)Math.pow(10, offset+1) % 10;
        if (mode == 1) return program[i+offset];
        return program[program[i+offset]];
    }

    static int runProgram(int input) throws IOException {
        int[] program = AoC.readNumbersSplitBy(",", "05/input.txt");
        int i = 2;
        // only one input so don't need to implement opcode 3
        program[program[1]] = input;  

        while(opcode(program, i) != 99) {
            if (opcode(program, i) == 4) {
                i += 2;
                continue;
            }

            int a = value(program, i, 1);
            int b = value(program, i, 2);
            int x = program[i+3];

            switch (opcode(program, i)) {
                case 1:
                    program[x] = a + b;
                    break;
                case 2:
                    program[x] = a * b;
                    break;
                case 5:
                
                    if (a != 0) {
                        i = b;
                        continue;
                    }
                    i--;
                    break;
                case 6:
                    if (a == 0) {
                        i = b;
                        continue;
                    }
                    i--;
                    break;
                case 7:
                    program[x] = a < b ? 1 : 0;
                    break;
                case 8:
                    program[x] = a == b ? 1 : 0;
                    break;
            }
            
            i += 4;
        }

        return value(program, i-2, 1);
    }

    public static void main(String[] _args) throws IOException {
        AoC.printResult(1, runProgram(1));
        AoC.printResult(2, runProgram(5));
    }
}
