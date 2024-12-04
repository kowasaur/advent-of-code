def found(x: int, y: int, lines: list[str]) -> int:
    if lines[y][x] != 'X':
        return 0

    result = 0

    if lines[y][x:x + 4] == "XMAS":
        result += 1

    if lines[y][x - 3:x] == "SAM":
        result += 1

    if y < len(lines) - 3 and lines[y + 1][x] == "M" and lines[
            y + 2][x] == "A" and lines[y + 3][x] == "S":
        result += 1

    if y > 2 and lines[y - 1][x] == "M" and lines[y - 2][x] == "A" and lines[
            y - 3][x] == "S":
        result += 1

    if y < len(lines) - 3 and x < len(lines[y]) - 3 and lines[y + 1][
            x + 1] == "M" and lines[y + 2][x + 2] == "A" and lines[y +
                                                                   3][x +
                                                                      3] == "S":
        result += 1

    if y > 2 and x > 2 and lines[y - 1][x - 1] == "M" and lines[y - 2][
            x - 2] == "A" and lines[y - 3][x - 3] == "S":
        result += 1

    if y < len(lines) - 3 and x > 2 and lines[y + 1][x - 1] == "M" and lines[
            y + 2][x - 2] == "A" and lines[y + 3][x - 3] == "S":
        result += 1

    if y > 2 and x < len(lines[y]) - 3 and lines[y - 1][x + 1] == "M" and lines[
            y - 2][x + 2] == "A" and lines[y - 3][x + 3] == "S":
        result += 1

    return result


def found2(x: int, y: int, lines: list[str]) -> int:
    if lines[y][x] != 'A' or y < 1 or x < 1 or y >= len(lines) - 1 or x >= len(
            lines[y]) - 1:
        return 0

    corners = [(1, 1), (-1, -1), (-1, 1), (1, -1)]

    ms = 0
    ss = 0
    for dx, dy in corners:
        if lines[y + dy][x + dx] == 'M':
            ms += 1
        elif lines[y + dy][x + dx] == 'S':
            ss += 1

    return 1 if ms == 2 and ss == 2 and lines[y + 1][x + 1] != lines[y - 1][
        x - 1] else 0


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    p1 = 0
    p2 = 0

    for y, line in enumerate(lines):
        for x, _ in enumerate(line):
            p1 += found(x, y, lines)
            p2 += found2(x, y, lines)

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
