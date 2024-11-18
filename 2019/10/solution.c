#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>

#define LIST_INITIAL_SIZE 20 // Arbitrary small size

typedef struct {
    int x;
    int y;
    int quadrant; // lol not really a vec2 now
} Vec2;
 
typedef struct {
    Vec2* data;
    int length;
    int capacity; // Should not use outside of list_* functions.
} List;

List list_init(void)
{
    List list;
    list.length = 0;
    list.capacity = LIST_INITIAL_SIZE;
    list.data = malloc(sizeof(Vec2) * list.capacity);
    return list;
}

void list_add(List* list, Vec2 ratio)
{
    if (list->length >= list->capacity) { // list is full
        list->capacity *= 2;
        list->data = realloc(list->data, sizeof(Vec2) * list->capacity);
    }
    list->data[list->length] = ratio;
    list->length++;
}


// Sorted from largest to smallest
void list_sort(List* list)
{
    bool swapped = true;
    while (swapped) {
        swapped = false;
        for (int i = 1; i < list->length; i++) {
            Vec2 left = list->data[i - 1];
            Vec2 right = list->data[i];
            if (left.quadrant > right.quadrant || (left.quadrant == right.quadrant && left.y * right.x > right.y * left.x)) {
                list->data[i - 1] = right;
                list->data[i] = left;
                swapped = true;
            }
        }
    }
}


int lineWidth(FILE* file) {
    char c;
    while ((c = fgetc(file)) != '\n'); 
    return ftell(file) - 1; // - 1 for newline character
}

int fileSize(FILE* file) {
    fseek(file, 0, SEEK_END);
    return ftell(file);
}

// Found here https://stackoverflow.com/a/72020462/14746108
int gcd(int a, int b){
    if (a && b) for(;(a %= b) && (b %= a););
    return a | b;
}

bool* readAsteroids(int* width, int* height) {
    FILE* file = fopen("input.txt", "r");
    *width = lineWidth(file);
    *height = fileSize(file) / (*width + 1);

    bool* asteroids = calloc(*width * *height, sizeof(bool));
    fseek(file, 0, SEEK_SET);
    for (int y = 0; y < *height; y++) {
        for (int x = 0; x < *width; x++) {
            char c = fgetc(file);
            if (c == '#') {
                asteroids[y * *width + x] = true;
            }
        }
        fgetc(file); // newline character
    }

    fclose(file);
    return asteroids;
}

List createGradients(int width, int height) {
    List gradients = list_init();
    list_add(&gradients, (Vec2){0, -1, 1});
    list_add(&gradients, (Vec2){1, 0, 2});
    list_add(&gradients, (Vec2){0, 1, 3});
    list_add(&gradients, (Vec2){-1, 0, 4});

    for (int y = 1; y < height; y++) {
        for (int x = 1; x < width; x++) {
            if (gcd(x, y) == 1) {
                list_add(&gradients, (Vec2){x, -y, 1});
                list_add(&gradients, (Vec2){x, y, 2});
                list_add(&gradients, (Vec2){-x, y, 3});
                list_add(&gradients, (Vec2){-x, -y, 4});
            }
        }
    }
    return gradients;
}

Vec2 detected(bool* asteroids, int width, int height, Vec2 position, Vec2 gradient) {
    for (int x = position.x + gradient.x, y = position.y + gradient.y; x < width && y < height && x >= 0 && y >= 0; x += gradient.x, y += gradient.y) {
        if (asteroids[y * width + x]) {
            return (Vec2){x, y};
        } 
    }
    return (Vec2){-1, -1};
}

int detectableAsteroids(bool* asteroids, int width, int height, Vec2 position, List gradients) {
    int visible = 0;

    for (int i = 0; i < gradients.length; i++) {
        Vec2 gradient = gradients.data[i];
        Vec2 detectedAsteroid = detected(asteroids, width, height, position, gradient);
        if (detectedAsteroid.x != -1) {
            visible++;
        }
    }

    return visible;
}

int main() {
    int width, height;
    bool* asteroids = readAsteroids(&width, &height);
    List gradients = createGradients(width, height);

    int mostAsteroids = 0;
    Vec2 station;
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            if (asteroids[y * width + x]) {
                int visible = detectableAsteroids(asteroids, width, height, (Vec2){x, y}, gradients);
                if (visible > mostAsteroids) {
                    mostAsteroids = visible;
                    station = (Vec2){x, y};
                }
            }
        }
    }

    printf("Part 1: %d\n", mostAsteroids);

    list_sort(&gradients);

    Vec2 zappedAsteroid;
    int i = 0;
    for (int zapped = 0; zapped < 200; zapped++) {
        while (true) {
            Vec2 gradient = gradients.data[i];
            zappedAsteroid = detected(asteroids, width, height, station, gradient);
            i = (i + 1) % gradients.length;

            if (zappedAsteroid.x != -1) {
                asteroids[zappedAsteroid.y * width + zappedAsteroid.x] = false;
                break;
            } 
        }
    }

    printf("Part 2: %d\n", zappedAsteroid.x * 100 + zappedAsteroid.y);

    free(asteroids);
    free(gradients.data);
    return 0;
}
