import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("25/input.txt");

        // used this to make a graph to visually get the answer
        // System.out.println("graph {");
        // int c = 0;
        // for (String line : lines) {
        //     var parts = line.split("[: ]+");
        //     for (int i = 1; i < parts.length; i++) {
        //         System.out.println(parts[0] + " -- " + parts[i] + " [color=\"#" + String.format("%06X", c) +"\", penwidth=8];");
        //         c += 1200;
        //     }
        // }
        // System.out.println("}");

        for (String line : lines) {
            var parts = line.split("[: ]+");
            var key = parts[0];
            var vk = Vertex.get(key);
            for (int i = 1; i < parts.length; i++) {
                var val = parts[i];
                if (key.equals("njn") && val.equals("xtx")) continue;
                if (key.equals("tmb") && val.equals("gpj")) continue;
                if (key.equals("rhh") && val.equals("mtc")) continue;
                var vv = Vertex.get(val);
                vk.edges.add(val);
                vv.edges.add(key);
            }
        }

        AoC.printResult(1, Vertex.groupSize("fmm") * Vertex.groupSize("tdt"));
    }
}

class Vertex {
    static HashMap<String, Vertex> all = new HashMap<String, Vertex>();

    List<String> edges = new ArrayList<String>();

    public static Vertex get(String key) {
        var vk = all.get(key);
        if (vk != null) return vk;
        all.put(key, new Vertex());
        return all.get(key);
    }

    public static int groupSize(String key) {
        var discovered = new HashSet<String>();
        groupSize(key, discovered);
        return discovered.size();
    }

    static void groupSize(String key, Set<String> discovered) {
        if (discovered.contains(key)) return;
        discovered.add(key);
        for (String edge : all.get(key).edges) {
            groupSize(edge, discovered);
        }
    }
}
