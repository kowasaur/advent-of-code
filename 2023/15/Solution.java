import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static int hash(String input) {
        int curr = 0;
        for (int i = 0; i < input.length(); i++) {
            curr += input.charAt(i);
            curr = (curr * 17) % 256;
        }
        return curr;
    }

    public static void main(String[] _args) throws IOException {
        var steps = AoC.readFile("15/input.txt").split(",");

        AoC.printResult(1, AoC.mapSum(steps, Solution::hash));

        var boxes = new Box[256];
        for (int i = 0; i < 256; i++) boxes[i] = new Box();

        for (String step : steps) {
            var parts = step.split("-|=");
            var label = parts[0];
            var box = boxes[hash(label)];
            int ind = box.labels.indexOf(label);
            if (parts.length == 2) { // =
                int length = Integer.parseInt(parts[1]);
                if (ind != -1) {
                    box.labels.set(ind, label);
                    box.lengths.set(ind, length);
                } else {
                    box.labels.add(label);
                    box.lengths.add(length);
                }
            } else { // -
                if (ind != -1) {
                    box.labels.remove(ind);
                    box.lengths.remove(ind);
                }
            }
        }

        int focusing_power = 0;
        for (int i = 0; i < boxes.length; i++) {
            var box_lengths = boxes[i].lengths;
            for (int j = 0; j < box_lengths.size(); j++) {
                focusing_power += (i+1) * (j+1) * box_lengths.get(j);
            }
        }
        AoC.printResult(2, focusing_power);
    }
}

class Box {
    public ArrayList<String> labels = new ArrayList<String>();
    public ArrayList<Integer> lengths = new ArrayList<Integer>();
}
