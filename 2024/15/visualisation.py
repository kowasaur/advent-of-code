# Visualisation of part 2

import curses
from time import sleep

Vec2 = tuple[int, int]


def movevert(grid: list[list[str]], pos: Vec2, dir: Vec2) -> bool:
    x, y = pos
    dx, dy = dir

    ny = y + dy

    if grid[y][x + dx] == "#":
        return False
    if grid[ny][x] == ".":
        return True

    if grid[ny][x] == "]":
        x -= 1

    if movevert(grid, (x, ny), dir) and movevert(grid, (x + 1, ny), dir):
        return True

    return False


def movefr(pos: Vec2, grid: list[list[str]], dir: Vec2):
    x, y = pos
    _, dy = dir
    ny = y + dy

    if grid[ny][x] in ["#", "."]:
        return

    if grid[ny][x] == "]":
        x -= 1

    movefr((x, ny), grid, dir)
    movefr((x + 1, ny), grid, dir)

    grid[ny + dy][x] = "["
    grid[ny + dy][x + 1] = "]"
    grid[ny][x] = "."
    grid[ny][x + 1] = "."


def main():
    stdscr = curses.initscr()

    with open("input.txt") as f:
        parts = f.read().split("\n\n")

    grid = []
    for line in parts[0].split("\n"):
        x = []
        grid.append(x)
        for c in line:
            if c == "@":
                x.append("@")
                x.append(".")
            elif c == "O":
                x.append("[")
                x.append("]")
            else:
                x.append(c)
                x.append(c)

    x, y = 0, 0
    for y, line in enumerate(grid):
        do_break = False
        for x, c in enumerate(line):
            if c == "@":
                grid[y][x] = "."
                do_break = True
                break
        if do_break:
            break

    for move in parts[1]:
        if move == "<":
            dx, dy = -1, 0
        elif move == ">":
            dx, dy = 1, 0
        elif move == "^":
            dx, dy = 0, -1
        elif move == "v":
            dx, dy = 0, 1
        else:
            continue

        nx = x + dx
        ny = y + dy
        if grid[ny][nx] == "#":
            continue

        if grid[ny][nx] == ".":
            x, y = nx, ny
            continue

        bx, by = nx, ny

        if dy == 0:  # horizontal
            c = "]" if dx == -1 else "["

            while grid[by][bx] == c:
                bx += dx * 2

            if grid[by][bx] == "#":
                continue

            if dx == -1:
                for ax in range(bx, nx):
                    grid[by][ax] = grid[by][ax + 1]
            else:
                for ax in range(bx, nx, -1):
                    grid[by][ax] = grid[by][ax - 1]
                by += dy * 2

            grid[ny][nx] = "."
            x, y = nx, ny
        else:
            if movevert(grid, (x, y), (dx, dy)):
                movefr((x, y), grid, (dx, dy))
                x, y = nx, ny

        stdscr.move(0, 0)
        for y1, line in enumerate(grid):
            for x1, c in enumerate(line):
                stdscr.addstr("@" if (x1, y1) == (x, y) else c)
            stdscr.move(y1 + 1, 0)
        stdscr.refresh()
        sleep(0.05)

    curses.endwin()


if __name__ == '__main__':
    main()
