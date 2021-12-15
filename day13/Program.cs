using System;
using System.IO;
using System.Collections.Generic;

class Program {

    struct Fold {
        public int line;
        public bool isHorizontal; // y=...
        
        public Fold(string instruction) {
            string[] data = instruction.Split(" ")[2].Split("=");
            Int32.TryParse(data[1], out this.line);
            this.isHorizontal = data[0] == "y";
        }
    }

    static int PaperHeight(bool[,] paper) {
        return paper.GetUpperBound(0) + 1;
    }

    static int PaperWidth(bool[,] paper) {
        return paper.GetUpperBound(1) + 1;
    }

    static bool[,] FoldPaper(bool[,] paper, Fold fold) {
        int newHeight = fold.isHorizontal ? fold.line : PaperHeight(paper);
        int newWidth = fold.isHorizontal ? PaperWidth(paper) : fold.line;
        bool[,] newPaper = new bool[newHeight, newWidth];
    
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                newPaper[i, j] = paper[i, j];

                int mirrorIndex = 2 * fold.line - (fold.isHorizontal ? i : j);
                bool inRange = mirrorIndex < (fold.isHorizontal ? PaperHeight(paper) : PaperWidth(paper));
                bool mirroredDot = fold.isHorizontal ? paper[mirrorIndex, j] : paper[i, mirrorIndex]; 
                if (inRange && mirroredDot) newPaper[i, j] = true;
            }
        }

        return newPaper;
    }

    static void Part1(bool[,] paper, Fold fold) {
        bool[,] newPaper = FoldPaper(paper, fold);
        int total = 0;

        for (int i = 0; i < PaperHeight(newPaper); i++) {
            for (int j = 0; j < PaperWidth(newPaper); j++) {
                if (newPaper[i, j]) total++;
            }
        }

        Console.WriteLine(total);
    }

    static void Part2(bool[,] paper, string[] instructions) {
        foreach (string instruc in instructions) {
            paper = FoldPaper(paper, new Fold(instruc));
        }
        PrintPaper(paper);
    }

    static void PrintPaper(bool[,] paper) {
        for (int i = 0; i < PaperHeight(paper); i++) {
            for (int j = 0; j < PaperWidth(paper); j++) {
                Console.Write(paper[i, j] ? "#" : ".");
            }
            Console.Write("\n");
        }
    }

    static void Main(string[] args) {
        string[] file = File.ReadAllText("./input.txt").Split("\n\n");
    
        List<int> xs = new List<int>();
        List<int> ys = new List<int>();
        int maxX = 0;
        int maxY = 0;

        foreach (string line in file[0].Split("\n")) {
            string[] coordinates = line.Split(",");
            Int32.TryParse(coordinates[0], out int x);
            Int32.TryParse(coordinates[1], out int y);
            xs.Add(x);
            ys.Add(y);
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }
  
        bool[,] paper = new bool[maxY + 1, maxX + 1];

        for (int i = 0; i < xs.Count; i++) {
            paper[ys[i], xs[i]] = true;
        }
        
        string[] instructions = file[1].Split("\n");

        if (args[0] == "1") Part1(paper, new Fold(instructions[0]));
        else Part2(paper, instructions);
    }

}
