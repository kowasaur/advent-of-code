import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import utils.*;

class Solution {
    static ArrayList<String> orbitPath(HashMap<String, String> orbits, String key) {
        if (!orbits.containsKey(key)) return new ArrayList<>();
        var result = orbitPath(orbits, orbits.get(key));
        result.add(orbits.get(key));
        return result;
    }

    public static void main(String[] _args) throws IOException {
        // Don't need to know this but this is what this is
        // https://en.wikipedia.org/wiki/Pseudoforest#Directed_pseudoforests
        var orbits = new HashMap<String, String>();

        for (String line : AoC.readLines("06/input.txt")) {
            String[] parts = line.split("\\)");
            orbits.put(parts[1], parts[0]);
        }

        int total_orbits = 0;
        var paths = new HashMap<String, ArrayList<String>>();

        for (String key : orbits.keySet()) {
            paths.put(key, orbitPath(orbits, key));
            total_orbits += paths.get(key).size();
        }

        AoC.printResult(1, total_orbits);

        var p1 = paths.get("YOU");
        var p2 = paths.get("SAN");
        int i = 0;
        while (p1.get(i) == p2.get(i)) i++;

        AoC.printResult(2, p1.size() + p2.size() - (i+1)*2);
    }
}
