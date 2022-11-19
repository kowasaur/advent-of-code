import sys

with open("./01/input.txt") as f:
    entries = [int(l) for l in f.readlines()]

def part1():
    for e1 in entries:
        for e2 in entries:
            if e1 + e2 == 2020:
                print(e1 * e2)
                exit()

def part2():
    for e1 in entries:
        for e2 in entries:
            for e3 in entries:
                if e1 + e2 + e3 == 2020:
                    print(e1 * e2 * e3)
                    exit()

if sys.argv[1] == '1':
    part1()
elif sys.argv[1] == '2':
    part2()
