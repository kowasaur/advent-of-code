package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class AoC {
    public static int[] readNumbersSplitBy(String sep, String path) {
        String[] things = null;
        try {
            things = Files.readString(Paths.get(path)).trim().split(sep);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        return intArray(things);
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
