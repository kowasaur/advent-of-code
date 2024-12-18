from collections import deque

Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]

WIDTH = 70
FIRST = 1024


def length(first_1024):
    frontier = deque([(0, 0)])
    visited = {(0, 0): 0}

    while frontier:
        x, y = frontier.popleft()
        if (x, y) == (WIDTH, WIDTH):
            return visited[(x, y)]

        for dx, dy in DIRS:
            nx, ny = x + dx, y + dy
            if 0 <= nx <= WIDTH and 0 <= ny <= WIDTH and (
                    nx, ny) not in visited and (nx, ny) not in first_1024:
                visited[(nx, ny)] = visited[(x, y)] + 1
                frontier.append((nx, ny))

    return -1


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    when_corrupted = set()
    for line in lines[:FIRST]:
        x, y = map(int, line.split(","))
        when_corrupted.add((x, y))

    print("Part 1:", length(when_corrupted))

    p2 = ""  # Make pyright shutup
    for line in lines[FIRST:]:
        x, y = map(int, line.split(","))
        when_corrupted.add((x, y))
        if length(when_corrupted) < 0:
            p2 = f"{x},{y}"
            break

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
