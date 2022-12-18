// For part 2 I got help from https://www.reddit.com/r/adventofcode/comments/zo27vf/2022_day_17_part_2_rocks_fall_nobody_dies/

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <sys/param.h>

#define CHAMBER_WIDTH 7
#define CHAMBER_HEIGHT 10000

typedef struct CycleInfo {
    int rock_index;
    int height;
} CycleInfo;

typedef long long ll;

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

void resetChamber() {
    for (int y = 1; y < CHAMBER_HEIGHT; y++) {
        for (int x = 0; x < CHAMBER_WIDTH; x++) chamber[y][x] = false;
    }
    for (int x = 0; x < CHAMBER_WIDTH; x++) chamber[0][x] = true;
}

ll rockTowerHeight(ll rocks_count, bool *move_left, long file_size) {
    int height = 1; // have to account for this later
    int i = -1;

    CycleInfo cycle_info[5][file_size];
    for (int r = 0; r < 5; r++) {
        for (int c = 0; c < file_size; c++) cycle_info[r][c].height = 0;
    }

    for (int rock_index = 0; rock_index < rocks_count; rock_index++) {
        const int r = rock_index % 5;
        const int next_i = (i + 1) % file_size;

        if (cycle_info[r][next_i].height) {
            int h = cycle_info[r][next_i].height ;
            int ri = cycle_info[r][next_i].rock_index;
            if ((rocks_count - ri) % (rock_index - ri) == 0) {
                resetChamber();
                return h - 1 + (height - h) * (rocks_count - ri) / (rock_index - ri);
            }
        }

        cycle_info[r][next_i].rock_index = rock_index;
        cycle_info[r][next_i].height = height;

        // bottom left of rock coordinates
        int bl_x = 2;
        int bl_y = height + 3;

        while (true) {
            i = (i + 1) % file_size;
            int new_bl_x = move_left[i] ? bl_x - 1 : bl_x + 1;
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

    resetChamber();
    return height - 1;
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

    printf("Part 1: %lld\n", rockTowerHeight(2022, move_left, file_size));
    printf("Part 2: %lld\n", rockTowerHeight(1000000000000, move_left, file_size));

    return 0;
}
