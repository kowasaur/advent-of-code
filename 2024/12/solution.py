Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]

CORNERS = [[(0, 1), (1, 0)], [(0, 1), (-1, 0)], [(0, -1), (1, 0)],
           [(0, -1), (-1, 0)]]


def in_bounds(pos: Vec2, lines: list[str]) -> bool:
    return 0 <= pos[0] < len(lines[0]) - 1 and 0 <= pos[1] < len(lines)


def findstats(x, y, lines, seen):
    if (x, y) in seen:
        return 0, 0
    a = 1
    perm = 0
    seen.add((x, y))
    c = lines[y][x]
    for dx, dy in DIRS:
        nx, ny = x + dx, y + dy
        if not in_bounds((nx, ny), lines) or lines[ny][nx] != c:
            perm += 1
        else:
            da, dp = findstats(nx, ny, lines, seen)
            a += da
            perm += dp
    return a, perm


def findstats2(x, y, lines, seen):
    if (x, y) in seen:
        return 0, 0
    a = 1
    sides = 0
    seen.add((x, y))
    c = lines[y][x]

    num_surrounding = sum(
        in_bounds((x + dx, y + dy), lines) and lines[y + dy][x + dx] == c
        for dx, dy in DIRS)

    if num_surrounding == 0:
        return a, 4

    if num_surrounding == 1:
        sides = 2

    else:
        for corner in CORNERS:
            if all(
                    in_bounds((x + dx,
                               y + dy), lines) and lines[y + dy][x + dx] == c
                    for dx, dy in corner) and all(not in_bounds(
                        (x + dx, y + dy), lines) or lines[y + dy][x + dx] != c
                                                  for dx, dy in DIRS
                                                  if (dx, dy) not in corner):
                sides += 1
            ddx = corner[0][0] + corner[1][0]
            ddy = corner[0][1] + corner[1][1]
            nx, ny = x + ddx, y + ddy
            if in_bounds((nx, ny), lines) and lines[ny][nx] != c and all(
                    in_bounds((x + dx,
                               y + dy), lines) and lines[y + dy][x + dx] == c
                    for dx, dy in corner):
                sides += 1

    for dx, dy in DIRS:
        nx, ny = x + dx, y + dy
        if in_bounds((nx, ny), lines) and lines[ny][nx] == c:
            da, dp = findstats2(nx, ny, lines, seen)
            a += da
            sides += dp
    return a, sides


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    p1 = 0
    p2 = 0
    seen = set()
    seen2 = set()

    for y, line in enumerate(lines):
        for x, _ in enumerate(line[:-1]):
            a, p = findstats(x, y, lines, seen)
            p1 += a * p
            a, p = findstats2(x, y, lines, seen2)
            p2 += a * p

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == '__main__':
    main()
