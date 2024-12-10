import re
from math import *

Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


def func(line: str) -> int:
    return 0


def main():
    with open("input.txt") as f:
        lines = f.readlines()
        # file = f.read()

    p1 = 0
    p2 = 0

    for line in lines:
        p1 += func(line)

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
