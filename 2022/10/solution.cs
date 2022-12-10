using System;
using System.IO;

class Solution {
    static int result = 0;
    static int cycle = 0;
    static int X = 1;
    static string buffer = "";

    static void Tick() {
        int current_pixel = cycle % 40;
        buffer += X-1 <= current_pixel && current_pixel <= X+1 ? '#' : '.';
        if (current_pixel == 39) buffer += '\n';

        cycle++;
        if ((cycle - 20) % 40 == 0) result += cycle * X;
    }

    static void Main() {
        foreach (string line in File.ReadLines("input.txt")) {
            Tick();
            if (line == "noop") continue;
            
            Tick();
            X += Int32.Parse(line.Split(' ')[1]);
        }

        Console.WriteLine($"Part 1: {result}");
        Console.WriteLine("Part 2: ");
        Console.Write(buffer);
    }
}
