def determineAntinode(poses: list[tuple[int, int]],
                      antinodes: set[tuple[int, int]], width: int, height: int):
    for x1, y1 in poses:
        for x2, y2 in poses:
            if x1 == x2 and y1 == y2:
                continue
            dx = x2 - x1
            dy = y2 - y1
            nx = x1 - dx
            ny = y1 - dy
            if 0 <= nx < width and 0 <= ny < height:
                antinodes.add((nx, ny))


def determineAntinode2(poses: list[tuple[int, int]], antinodes: set[tuple[int,
                                                                          int]],
                       width: int, height: int):
    for x1, y1 in poses:
        for x2, y2 in poses:
            if x1 == x2 and y1 == y2:
                continue
            dx = x2 - x1
            dy = y2 - y1
            nx = x1
            ny = y1
            while 0 <= nx < width and 0 <= ny < height:
                antinodes.add((nx, ny))
                nx += dx
                ny += dy


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    width = len(lines[0]) - 1
    height = len(lines)

    locs = {}
    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            if c != ".":
                if c not in locs:
                    locs[c] = []
                locs[c].append((x, y))

    antinodes = set()
    antinodes2 = set()

    for c in locs:
        determineAntinode(locs[c], antinodes, width, height)
        determineAntinode2(locs[c], antinodes2, width, height)

    print("Part 1:", len(antinodes))

    print("Part 2:", len(antinodes2))


if __name__ == '__main__':
    main()
