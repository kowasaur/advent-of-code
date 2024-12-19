from functools import cache


def func(line: str, patterns: list[str]) -> bool:
    if len(line) == 0:
        return True

    for pattern in patterns:
        l = len(pattern)
        if line[:l] == pattern:
            maybe = func(line[l:], patterns)
            if maybe:
                return True

    return False


@cache
def func2(line: str) -> int:
    if len(line) == 0:
        return 1

    result = 0

    for pattern in patterns:
        l = len(pattern)
        if line[:l] == pattern:
            result += func2(line[l:],)

    return result


def main():
    global patterns

    with open("input.txt") as f:
        patterns, designs = f.read().split("\n\n")

    patterns = patterns.split(", ")
    designs = designs.split("\n")[:-1]

    p1 = 0
    p2 = 0

    for line in designs:
        p1 += func(line, patterns)
        p2 += func2(line)

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
