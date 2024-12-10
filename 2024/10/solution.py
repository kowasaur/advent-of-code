Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


def in_bounds(pos: Vec2, lines: list[str]) -> bool:
    return 0 <= pos[0] < len(lines[0]) - 1 and 0 <= pos[1] < len(lines)


def trailheads(pos: Vec2, lines: list[str], seen: set[Vec2]):
    c = int(lines[pos[1]][pos[0]])
    if c == 9:
        seen.add(pos)
        return

    for dx, dy in DIRS:
        x, y = pos
        x += dx
        y += dy
        if in_bounds((x, y), lines):
            if int(lines[y][x]) == c + 1:
                trailheads((x, y), lines, seen)


def trailheads2(pos: Vec2, lines: list[str]) -> int:
    c = int(lines[pos[1]][pos[0]])
    if c == 9:
        return 1

    result = 0

    for dx, dy in DIRS:
        x, y = pos
        x += dx
        y += dy
        if in_bounds((x, y), lines):
            if int(lines[y][x]) == c + 1:
                result += trailheads2((x, y), lines)

    return result


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    p1 = 0
    p2 = 0

    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            if c == "0":
                seen = set()
                trailheads((x, y), lines, seen)
                p1 += len(seen)
                p2 += trailheads2((x, y), lines)

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == '__main__':
    main()
