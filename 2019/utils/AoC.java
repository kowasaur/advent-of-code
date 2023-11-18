package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AoC {
    static String file_name = "input";

    public static void useSample() {
        file_name = "sample";
    }

    public static int[] readNumbersSplitBy(String sep) {
        String[] things = null;
        try {
            things = Files.readString(Paths.get("04/input.txt")).trim().split(sep);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        return Stream.of(things).mapToInt(Integer::parseInt).toArray();
    }

    public static void print(int[] thing) {
        for (int t : thing) System.out.println(t);
    }

    public static <T> void print(T[] thing) {
        for (T t : thing) System.out.println(t);
    }

    public static void printResult(int part, long answer) {
        System.out.printf("Part %d: %d\n", part, answer);
    }
}
