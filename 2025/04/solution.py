Vec2 = tuple[int, int]


def in_bounds(pos: Vec2, lines: list[str]) -> bool:
    return 0 <= pos[0] < len(lines[0]) and 0 <= pos[1] < len(lines)


def num_surrounding(x: int, y: int, lines: list[str]) -> int:
    result = 0
    for dy in [-1, 0, 1]:
        for dx in [-1, 0, 1]:
            if dx == 0 and dy == 0:
                continue
            nx, ny = x + dx, y + dy
            if in_bounds((nx, ny), lines) and lines[ny][nx] == "@":
                result += 1
    return result


def can_remove(lines: list[str]) -> list[Vec2]:
    to_remove = []
    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            if c == "@" and num_surrounding(x, y, lines) < 4:
                to_remove.append((x, y))
    return to_remove


def main(input_file: str = "input.txt"):
    with open(input_file) as f:
        lines = f.read().splitlines()

    p1 = len(can_remove(lines))
    p2 = 0

    while True:
        to_remove = can_remove(lines)
        p2 += len(to_remove)
        if not to_remove:
            break
        for x, y in to_remove:
            lines[y] = lines[y][:x] + "." + lines[y][x + 1 :]

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == "__main__":
    main()
