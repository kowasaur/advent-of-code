#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define SIZE 20000
#define LINE_SIZE 1500

static int wires[SIZE][SIZE] = {0};

void parse_line(FILE *file, int idk_what_to_name_this) {
    char line_content[LINE_SIZE];
    fgets(line_content, LINE_SIZE, file);

    int x = SIZE/2;
    int y = SIZE/2;
    char dir;

    for (char *code = strtok(line_content, ","); code != NULL; code = strtok(NULL, ",")) {
        dir = code[0];
        char len_str[4];
        strcpy(len_str, code+1);
        int len = atoi(len_str);
        for (int i = 0; i < len; i++) {
            if (dir == 'R') x++;
            else if (dir == 'L') x--;
            else if (dir == 'D') y++;
            else y--;

            if (x >= SIZE || x < 0 || y >= SIZE || y < 0) {
                printf("not big enough");
                exit(1);
            }

            if (idk_what_to_name_this == wires[y][x]) wires[y][x]++;
        }
    }
}

int calc_dist(int x, int y, int min_dist) {
    int temp = abs(SIZE/2 - x) + abs(SIZE/2 - y);
    return temp > min_dist ? min_dist : temp;
}

void print(void) {
    for (int y = 0; y < SIZE; y++) {
        for (int x = 0; x < SIZE; x++) {
            if (x == SIZE/2 && y == SIZE/2) putchar('o');
            else if (wires[y][x] == 2) putchar('X');
            else if (wires[y][x] == 1) putchar('#');
            else putchar(' ');
        }
        putchar('\n');
    }
}

int main() {
    FILE *file;
    file = fopen("input.txt", "r");

    parse_line(file, 0);
    parse_line(file, 1);

    int min_dist = SIZE + SIZE;

    for (int y = 0; y < SIZE; y++) {
        for (int x = 0; x < SIZE; x++) {
            if (wires[y][x] == 2) min_dist = calc_dist(x,y, min_dist);
            else if (wires[y][x] > 2) {
                printf("error");
                return 1;
            }
        }
    }
  
    // print();

    printf("Part 1: %d", min_dist);

    return 0;
}
