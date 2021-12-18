#include <iostream>
#include <fstream>
#include <bitset>
#include <sstream>
#include <string>
#include <functional>

#define ll long long

using namespace std;

const function<ll(ll, ll)> operators[] = {
    plus<>(), multiplies<>(), [](ll a, ll b) { return min(a,b); }, 
    [](ll a, ll b) { return max(a,b); }, NULL, greater<>(), less<>(), equal_to<>()
};
const int defaults[] = {0, 1, 2147483647, 0};
string packet;
// Packet index
int pi = 0;

int section_value(int section_start, int section_size) {
    return stoll(packet.substr(section_start, section_size), 0, 2);
}

ll parse_packet() {
    int type = section_value(pi + 3, 3);
    pi += 6;

    if (type == 4) {
        string str_value;
        do {
            str_value += packet.substr(pi + 1, 4);
            pi += 5;
        } while (packet[pi - 5] != '0');

        return stoll(str_value, 0, 2);
    }

    ll value;
    pi++;
    // Length type
    if (packet[pi - 1] == '0') {
        int end = section_value(pi, 15) + pi + 15;
        pi += 15;
        if (type < 4) {
            value = defaults[type];
            while (pi < end) {
                value = operators[type](value, parse_packet());
            }
        } else {
            ll a = parse_packet();
            ll b = parse_packet();
            value = operators[type](a, b);
        }
    } else {
        int subpackets = section_value(pi, 11);
        pi += 11;
        if (type < 4) {
            value = defaults[type];
            for (unsigned i = 0; i < subpackets; i++) {
                value = operators[type](value, parse_packet());
            }
        } else {
            ll a = parse_packet();
            ll b = parse_packet();
            value = operators[type](a, b);
        }
        
    }

    return value;
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

    cout << parse_packet() << endl;

    return 0;
}
