import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import utils.*;

class Solution {
    static HashMap<String, List<Rule>> workflows = new HashMap<String, List<Rule>>();

    static HashMap<Character, Integer> parsePart(String line) {
        var part = new HashMap<Character, Integer>();
        var elems = line.substring(1, line.length()-1).split(",");
        for (String elem : elems) {
            part.put(elem.charAt(0), Integer.parseInt(elem.substring(2)));
        }
        return part;
    }

    static boolean accepted(List<Rule> workflow, HashMap<Character, Integer> part) {
        for (Rule rule : workflow) {
            if (rule.matches(part)) {
                if (rule.dest.equals("A")) return true;
                if (rule.dest.equals("R")) return false;
                return accepted(workflows.get(rule.dest), part);
            } 
        }
        System.out.println("this should never be reached");
        return false;
    }

    static boolean accepted(HashMap<Character, Integer> part) {
        return accepted(workflows.get("in"), part);
    }

    static int rating(HashMap<Character, Integer> part) {
        return part.values().stream().reduce((a,b) -> a + b).get();
    }

    static Map<Character, int[]> copyRanges(Map<Character, int[]> ranges) {
        return ranges.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().clone()));
    }

    static long combos(Rule rule, Map<Character, int[]> part_ranges) {
        if (rule.dest.equals("R")) return 0;
        if (rule.dest.equals("A")) return part_ranges.values().stream().mapToLong(r -> r[1] - r[0] + 1).reduce(1, (a,b) -> a*b);
        return acceptedCombinations(workflows.get(rule.dest), part_ranges);
    }

    static long acceptedCombinations(List<Rule> workflow, Map<Character, int[]> part_ranges) {
        long combs = 0;
        for (int i = 0; i < workflow.size()-1; i++) {
            var rule = workflow.get(i);
            var new_ranges = copyRanges(part_ranges);
            int[] r = new_ranges.get(rule.elem);
            int[] o = part_ranges.get(rule.elem);
            if (rule.greater) {
                r[0] = Math.max(r[0], rule.compare+1);
                o[1] = Math.min(o[1], rule.compare);
            } else {
                r[1] = Math.min(r[1], rule.compare-1);
                o[0] = Math.max(o[0], rule.compare);
            }
            combs += combos(rule, new_ranges);
        }
        return combs + combos(workflow.getLast(), part_ranges);
    }

    public static void main(String[] _args) throws IOException {
        var file = AoC.readFile("19/input.txt").split("\n\n");
        
        for (var ruleStr : file[0].split("\n")) {
            var line = ruleStr.split("\\{");
            var rules = Arrays.stream(line[1].substring(0, line[1].length()-1).split(",")).map(Rule::new).toList();
            workflows.put(line[0], rules);
        }

        var accepted_parts = Arrays.stream(file[1].split("\n")).map(Solution::parsePart).filter(Solution::accepted);
        AoC.printResult(1, accepted_parts.mapToInt(Solution::rating).sum());

        var part_ranges = new HashMap<Character, int[]>();
        for (char c : new char[] {'x', 'm', 'a', 's'}) part_ranges.put(c, new int[] {1, 4000});
        AoC.printResult(2, acceptedCombinations(workflows.get("in"), part_ranges));
    }
}

class Rule {
    char elem;
    boolean greater;
    int compare;
    String dest;

    public Rule(String str) {
        if (str.indexOf(">") == str.indexOf("<")) {
            dest = str;
            return;
        }

        elem = str.charAt(0);
        greater = str.charAt(1) == '>';
        var things = str.split(":");
        compare = Integer.parseInt(things[0].substring(2));
        dest = things[1];
    }

    public boolean matches(HashMap<Character, Integer> part) {
        if (elem == 0) return true;
        if (greater) return part.get(elem) > compare;
        return (part.get(elem) < compare);
    }
}
