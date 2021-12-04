#include <stdio.h>
#include <stdlib.h>

#define MAX_LINE_LENGTH 5

int main() {
    char line[MAX_LINE_LENGTH];

    FILE *file_pointer;
    file_pointer = fopen("./input.txt", "r");

    int previous[3];
    int increase_count = 0;

    fgets(line, MAX_LINE_LENGTH, file_pointer);
    previous[0] = atoi(line);
    fgets(line, MAX_LINE_LENGTH, file_pointer);
    previous[1] = atoi(line);
    fgets(line, MAX_LINE_LENGTH, file_pointer);
    previous[2] = atoi(line);

    while (fgets(line, MAX_LINE_LENGTH, file_pointer)) {
        int current = atoi(line);
        if (current != 0) {
            // Don't need to add previous[1] and previous[2] because it's on both sides
            if (current > previous[0]) increase_count++;
            previous[0] = previous[1];
            previous[1] = previous[2];
            previous[2] = current;
        }
    }

    fclose(file_pointer);

    printf("%d", increase_count);

    return 0;
}
