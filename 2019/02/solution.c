#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define SIZE 300 // big enough to not worry about the size

void readFile(int program[]) {
    FILE *file;
    char file_content[SIZE];
    file = fopen("input.txt", "r");
    fgets(file_content, SIZE, file);  // read the line
    
    int i = 0;
    for (char *num = strtok(file_content, ","); num != NULL; num = strtok(NULL, ",")) {
        program[i] = atoi(num);
        i++;
    }
}

void copyProgram(int init_program[], int program[]) {
    for (int i = 0; i < SIZE; i++) program[i] = init_program[i];
}

int runProgram(int program[], int input1, int input2) {
    program[1] = input1;
    program[2] = input2;

    int i = 0;
    while (program[i] != 99) {
        int a = program[program[i+1]];
        int b = program[program[i+2]];
        int x = program[i+3];
        if (program[i] == 1) {
            program[x] = a + b;
        } else {  // assume program[i] == 2
            program[x] = a * b;
        }
        i += 4;
    }

    return program[0];
}

int main() {
    int init_program[SIZE];
    readFile(init_program);
    int program[SIZE];
    copyProgram(init_program, program);

    printf("Part 1: %i\n", runProgram(program, 12, 2));

    for (int noun = 0; noun <= 99; noun++) {
        for (int verb = 0; verb <= 99; verb++) {
            copyProgram(init_program, program);
            if (runProgram(program, noun, verb) == 19690720) {
                printf("Part 2: %i\n", 100 * noun + verb);
                return 0;
            }
        }
    }

    return 1;
}
