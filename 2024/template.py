import re
from math import *
from collections import defaultdict

Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


def in_bounds(pos: Vec2, lines: list[str]) -> bool:
    return 0 <= pos[0] < len(lines[0]) and 0 <= pos[1] < len(lines)


def func(line: str) -> int:
    return 0


def main():
    # with open("sample.txt") as f:
    with open("input.txt") as f:
        lines = f.read().splitlines()
        # file = f.read()

    p1 = 0
    p2 = 0

    for line in lines:
        p1 += func(line)

    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            pass

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
