package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AoC {
    public static int[] readNumbersSplitBy(String sep, String path) throws IOException {
        return intArray(Files.readString(Paths.get(path)).trim().split(sep));
    }

    public static List<String> readLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    public static int[] intArray(String[] arr) {
        return Stream.of(arr).mapToInt(Integer::parseInt).toArray();
    }

    public static void print(int[] thing) {
        System.out.println(Arrays.toString(thing));
    }

    public static <T> void print(T[] thing) {
        System.out.println(Arrays.toString(thing));
    }

    public static <T> void print(List<T> thing) {
        System.out.println(Arrays.toString(thing.toArray()));
    }

    // Print an array with each element's index
    public static void printIndexes(int[] thing) {
        for (int i = 0; i < thing.length; i++) {
            System.out.print(i);
            System.out.print(": ");
            System.out.println(thing[i]);
        }
    }

    public static void printResult(int part, long answer) {
        System.out.printf("Part %d: %d\n", part, answer);
    }
}
