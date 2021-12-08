import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.function.Function;

public class Part2 {

    static final File FILE = new File("input.txt");
    static final int SIZE = 1000;
    
    public static void main(String[] args) throws FileNotFoundException {
        int[][] linesCount = new int[SIZE][SIZE]; // 2d array that shows how many lines cover each point
        int overlaps = 0;

        Scanner fileReader = new Scanner(FILE);
        while (fileReader.hasNext()) {
            String[] coordinates = fileReader.nextLine().split(" ");
            int[] start = parseCoordinate(coordinates[0]);
            int[] end = parseCoordinate(coordinates[2]);

            Function<Integer,Integer> increaseCurrent;
            int index = 1;

            if (start[0] == end[0]) {
                increaseCurrent = i -> ++linesCount[i][start[0]];
            } else if (start[1] == end[1]) {
                index = 0;
                increaseCurrent = i -> ++linesCount[start[1]][i];
            } else {       
                final int m = (end[1] - start[1]) / (end[0] - start[0]);

                increaseCurrent = i -> {
                    int c = m == 1 ? start[1] - start[0]: start[1] + start[0];
                    int x = m == 1 ? i - c : c - i;
                    return ++linesCount[i][x];};
            }

            for (int i = Math.min(start[index], end[index]); i <= Math.max(start[index], end[index]); i++) {
                int current = increaseCurrent.apply(i);
                if (current == 2) overlaps++;
            }
        }

        fileReader.close();

        System.out.println(overlaps);
    }

    static int[] parseCoordinate(String coord) {
        String[] nums = coord.split(",");
        int[] parsed = {Integer.parseInt(nums[0]), Integer.parseInt(nums[1])};
        return parsed;
    }

}
