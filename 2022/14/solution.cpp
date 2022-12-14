#include <algorithm>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <boost/algorithm/string_regex.hpp>

using std::vector, std::string;

// By looking at the input file, it seems nothing should exceed this
const int HEIGHT = 200;
const int WIDTH = 700;
bool cave[HEIGHT][WIDTH] = {false};

std::tuple<int, int> parseCoord(string str_coord) {
    vector<string> coord;
    boost::algorithm::split(coord, str_coord, boost::is_any_of(","));
    return {std::stoi(coord[0]), std::stoi(coord[1])};
}

bool newSand() {
    int x = 500, y = 0;
    while (true) {
        y++;
        if (y == HEIGHT) return false;
        if (!cave[y][x]);
        else if (!cave[y][x-1]) x--;
        else if (!cave[y][x+1]) x++;
        else {
            cave[y-1][x] = true;
            return true;
        }
    }
}

int findFloor() {
    for (int y = HEIGHT - 1; ; y--) {
        for (int x = 0; x < WIDTH; x++) if (cave[y][x]) return y + 2;
    }
}

int main() {
    // Parsing
    std::ifstream file ("./input.txt");
    string line;
    while (true) {
        std::getline(file, line);
        vector<string> paths;
        boost::algorithm::split_regex(paths, line, boost::regex(" -> "));

        vector<string>::const_iterator end = paths.cend() - 1;
        for (int i = 1; i < paths.size(); i++) {
            auto [x1, y1] = parseCoord(paths[i-1]);
            auto [x2, y2] = parseCoord(paths[i]);
            if (y1 == y2) {
                for (int j = std::min(x1, x2); j <= std::max(x1, x2); j++) cave[y1][j] = true;
            } else {
                for (int j = std::min(y1, y2); j <= std::max(y1, y2); j++) cave[j][x1] = true;
            }
        }

        if (file.eof()) break;
    }

    int sand_amount = 0;
    while (newSand()) sand_amount++;
    std::cout << "Part 1: " << sand_amount << '\n';

    int floor = findFloor();
    for (int x = 0; x < WIDTH; x++) cave[floor][x] = true;

    while(!cave[0][500]) {
        newSand();
        sand_amount++;
    }
    std::cout << "Part 2: " << sand_amount << '\n';
}
