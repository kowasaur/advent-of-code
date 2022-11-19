import 'dart:io';

const CHAR_POINTS = {
    ")": 3,
    "]": 57,
    "}": 1197,
    ">": 25137
};

const BRACKETS = {
    "(": ")", 
    "[": "]", 
    "{": "}", 
    "<": ">"
};

void main() {
    List<String> file = File("./input.txt").readAsLinesSync();
    int points = 0;

    for (final line in file) {
        List<String> openBrackets = [line[0]];

        for (int i = 1; i < line.length; i++) {
            String currentChar = line[i];
            if (BRACKETS.keys.contains(currentChar)) { // If open bracket
                openBrackets.add(currentChar);
            } else if (BRACKETS[openBrackets.last] == currentChar) {
                openBrackets.removeLast();
            } else {
                points += CHAR_POINTS[currentChar] as int; // Dart thinks it can be null
                break;
            }
        }
    }

    print(points);
}
