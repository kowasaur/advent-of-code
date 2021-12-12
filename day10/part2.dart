import 'dart:io';

const CHAR_POINTS = {
    "(": 1,
    "[": 2,
    "{": 3,
    "<": 4
};

const BRACKETS = {
    "(": ")", 
    "[": "]", 
    "{": "}", 
    "<": ">"
};

void main() {
    List<String> file = File("./input.txt").readAsLinesSync();
    List<int> scores = [];

    for (final line in file) {
        List<String> openBrackets = [line[0]];
        bool corrupted = false;

        for (int i = 1; i < line.length; i++) {
            String currentChar = line[i];
            if (BRACKETS.keys.contains(currentChar)) { // If open bracket
                openBrackets.add(currentChar);
            } else if (BRACKETS[openBrackets.last] == currentChar) {
                openBrackets.removeLast();
            } else {
                corrupted = true;
                break;
            }
        }

        // Skip corrupted lines
        if (corrupted) continue;

        int score = 0;

        for (final bracket in openBrackets.reversed) {
            score *= 5;
            score += CHAR_POINTS[bracket] as int;
        }

        scores.add(score);
    }

    scores.sort();
    int middle = scores[(scores.length / 2).floor()];

    print(middle);
}
