package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

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

    public static long[] longArray(String[] arr) {
        return Stream.of(arr).mapToLong(Long::parseLong).toArray();
    }

    public static long[] longArray(String line, String sep) {
        return longArray(line.split(sep));
    }

    public static <T> List<T> sorted(T[] arr, Comparator<? super T> comp) {
        return Arrays.stream(arr).sorted(comp).toList();
    }

    public static String reverse(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    public static String findFirst(String str, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        matcher.find();
        return matcher.group();
    }

    public static ArrayList<String> findAllMatches(String str, String regex) {
        ArrayList<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile(regex).matcher(str);
        while (matcher.find())
            allMatches.add(matcher.group());
        return allMatches;

    }

    // find the solutions to a quadratic of the form ax^2 + bx + c
    public static double[] quadratic(double a, double b, double c) {
        double t = Math.sqrt(b*b - 4*a*c);
        return new double[] {(-b - t)/(2*a), (-b + t)/(2*a)};
    }

    public static long gcd(long x, long y) {
        return y == 0 ? x : gcd(y, x % y);
    }

    public static long lcm(long x, long y) {
        return x*y / gcd(x, y);
    }

    public static long gcd(List<Long> nums) {
        return nums.stream().reduce(0l, (x, y) -> gcd(x, y));
    }

    // this and the gcd functions above modified from https://stackoverflow.com/a/40531215
    public static long lcm(List<Long> nums) {
        return nums.stream().reduce(1l, (x, y) -> x * (y / gcd(x, y)));
    }

    public static long lcm(LongStream nums) {
        return nums.reduce(1, (x, y) -> (x * (y / gcd(x, y))));
    }

    public static void print(int[] thing) {
        System.out.println(Arrays.toString(thing));
    }

    public static void print(double[] thing) {
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
