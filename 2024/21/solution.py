from functools import cache

NUMPAD = " 0A123456789"
DIRPAD = "<v> ^A"


@cache
def possibilities(fro: str, to: str, pad: str) -> list[str]:
    result = []
    i = pad.index(fro)
    j = pad.index(to)
    x, y = i % 3, i // 3
    jx, jy = j % 3, j // 3
    dx, dy = jx - x, jy - y
    if dx == 0:
        return ["^" * dy if dy > 0 else "v" * -dy]
    if dy == 0:
        return [">" * dx if dx > 0 else "<" * -dx]

    space_row = 0 if pad[0] == " " else 1

    xc = ">" * dx if dx > 0 else "<" * -dx
    yc = "^" * dy if dy > 0 else "v" * -dy

    if x != 0 or jy != space_row:
        result.append(yc + xc)
    if y != space_row or jx != 0:
        result.append(xc + yc)

    if len(result) == 0:
        print(fro)
        print(to)
        print(pad)

    return result


def moves(goals: list[str], pad: str) -> list[str]:
    fr_result = []
    for goal in goals:
        result = [""]
        i = pad.index("A")

        for c in goal:
            new_result = []
            for poss in possibilities(pad[i], c, pad):
                for r in result:
                    new_result.append(r + poss + "A")
            result = new_result
            i = pad.index(c)
        fr_result.extend(result)

    return fr_result


def func(line: str) -> int:
    first = moves([line], NUMPAD)
    second = moves(first, DIRPAD)
    third = moves(second, DIRPAD)
    return int(line[:-1]) * min(len(x) for x in third)


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    p1 = 0
    p2 = 0

    for line in lines:
        p1 += func(line)

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
