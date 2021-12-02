#include <stdio.h>
#include <stdlib.h>

#define MAX_LINE_LENGTH 5

int main() {
    char line[MAX_LINE_LENGTH];

    FILE *file_pointer;
    file_pointer = fopen("./input.txt", "r");

    fgets(line, MAX_LINE_LENGTH, file_pointer);
    int previous = atoi(line);
    int increase_count = 0;

    while (fgets(line, MAX_LINE_LENGTH, file_pointer)) {
        int current = atoi(line);
        if (current != 0) {
            if (current > previous) increase_count++;
            previous = current;
        }
    }

    fclose(file_pointer);

    printf("%d", increase_count);

    return 0;
}