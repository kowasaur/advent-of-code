#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <sys/param.h>

#define CHAMBER_WIDTH 7
#define CHAMBER_HEIGHT 10000

const bool rocks[5][4][4] = {
    {{true, true, true, true}},
    {
        {false, true, false},
        {true,  true, true},
        {false, true, false}
    },
    {
        {true,  true,  true},
        {false, false, true},
        {false, false, true}
    },
    {
        {true},
        {true},
        {true},
        {true}
    },
    {
        {true, true},
        {true, true}
    }
};

const int rock_width[] = {4, 3, 3, 1, 2};
const int rock_height[] = {1, 3, 3, 4, 2};

bool chamber[CHAMBER_HEIGHT][7] = {{true, true, true, true, true, true, true}};

bool doesCollide(int r, int bl_x, int bl_y) {
    for (int y = 0; y < 4; y++) {
        for (int x = 0; x < 4; x++) {
            if (rocks[r][y][x] && chamber[y+bl_y][x+bl_x]) return true;
        }
    }
    return false;
}

void printChamberWithNew(int r, int bl_y, int bl_x) {
    printf("-------\n");
    for (int y = 14; y >= 1; y--) {
        for (int x = 0; x < CHAMBER_WIDTH; x++) {
            char c = chamber[y][x] ? '#' : '.';
            if (bl_x <= x && x < bl_x + rock_width[r] && bl_y <= y && y < bl_y + rock_height[r]) {
                if (rocks[r][y - bl_y][x - bl_x]) c = '@';
            }
            putc(c, stdout);
        }
        putc('\n', stdout);
    }
    printf("-------\n\n");
}

void printChamber() {
    printf("-------\n");
    for (int y = 20; y >= 1; y--) {
        for (int x = 0; x < CHAMBER_WIDTH; x++) {
            putc(chamber[y][x] ? '#' : '.', stdout);
        }
        putc('\n', stdout);
    }
    printf("=======\n\n\n");
}

int main() {
    FILE *file = fopen("input.txt", "r");
    fseek(file, 0, SEEK_END);
    long file_size = ftell(file);
    fseek(file, 0, SEEK_SET);

    bool *move_left;
    move_left = malloc(file_size);
    int n = 0;
    char c;
    while ((c = fgetc(file)) != EOF) move_left[n++] = c == '<';

    int height = 1; // have to account for this later
    int i = -1;

    for (int rock_index = 0; rock_index < 2022; rock_index++) {
        const int r = rock_index % 5;

        // bottom left of rock coordinates
        int bl_x = 2;
        int bl_y = height + 3;

        while (true) {
            i++;
            int new_bl_x = move_left[i % file_size] ? bl_x - 1 : bl_x + 1;
            int new_bl_y = bl_y - 1; 

            if (new_bl_x >= 0 && rock_width[r] + new_bl_x <= CHAMBER_WIDTH && !doesCollide(r, new_bl_x, bl_y)) {
                bl_x = new_bl_x;
            }

            if (doesCollide(r, bl_x, new_bl_y)) break;
            bl_y = new_bl_y;
        }

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (rocks[r][y][x]) chamber[bl_y + y][bl_x + x] = true;
            }
        }

        height = MAX(bl_y + rock_height[r], height);
    }

    printf("Part 1: %d\n", height - 1);

    return 0;
}
