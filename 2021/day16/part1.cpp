#include <iostream>
#include <fstream>
#include <bitset>
#include <sstream>

using namespace std;

// Packet index
int pi = 0;
string packet;

int section_value(int section_start, int section_size) {
    return stoi(packet.substr(section_start, section_size), 0, 2);
}

// Returns the sum of all version numbers
int parse_packet() {
    int version = section_value(pi, 3);
    int type = section_value(pi + 3, 3);

    pi += 6;

    if (type == 4) {
        do {
            pi += 5;
        } while (packet[pi - 5] != '0');
        
        return version;
    }

    pi++;
    // Length type
    if (packet[pi - 1] == '0') {
        int end = section_value(pi, 15) + pi + 15;
        pi += 15;
        while (pi < end) {
            version += parse_packet();
        }
    } else {
        int subpackets = section_value(pi, 11);
        pi += 11;
        for (unsigned i = 0; i < subpackets; i++) {
            version += parse_packet();
        }
    }

    return version;
}

int main() {
    string hex_input;
    ifstream ("./input.txt") >> hex_input;

    for (unsigned i = 0; i < hex_input.length(); i++) {
        stringstream hex_stream;
        hex_stream << hex << hex_input[i];
        unsigned n;
        hex_stream >> n;
        bitset<4> b (n);
        packet += b.to_string();
    }

    cout << parse_packet();

    return 0;
}
