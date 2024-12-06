UP = (0, -1)
DOWN = (0, 1)
LEFT = (-1, 0)
RIGHT = (1, 0)

dirs = [UP, RIGHT, DOWN, LEFT]


def doesloop(x: int, y: int, rlines: list[str], width: int, height: int, ix,
             iy) -> bool:
    lines = [list(line) for line in rlines]
    lines[y][x] = "#"

    x = ix
    y = iy

    di = 0
    seen = set()
    while x >= 0 and y >= 0 and x < width and y < height:
        dx, dy = dirs[di]
        bruh = (x, y, dx, dy)
        if bruh in seen:
            return True
        seen.add(bruh)
        nx, ny = x + dx, y + dy

        if 0 <= nx < width and 0 <= ny < height and lines[ny][nx] == "#":
            di = (di + 1) % 4
        else:
            x, y = nx, ny

    return False


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    width = len(lines[0])
    height = len(lines)

    di = 0

    x = y = 0  # tell pyright to shutup
    for y, line in enumerate(lines):
        x = line.find("^")
        if x != -1:
            break

    ix = x
    iy = y

    seen = set()

    while x >= 0 and y >= 0 and x < width and y < height:
        seen.add((x, y))
        dx, dy = dirs[di]
        nx, ny = x + dx, y + dy

        if 0 <= nx < width and 0 <= ny < height and lines[ny][nx] == "#":
            di = (di + 1) % 4
        else:
            x, y = nx, ny

    print("Part 1:", len(seen))

    p2 = 0

    for y in range(height):
        for x in range(width):
            if lines[y][x] == ".":
                p2 += doesloop(x, y, lines, width, height, ix, iy)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
