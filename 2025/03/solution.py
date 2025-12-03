def func(line: str, digits=2) -> int:
    best = []
    start = 0
    for i in range(digits - 1, -1, -1):
        max_j = start
        max_c = int(line[start])

        section = line[start:-i] if i != 0 else line[start:]

        for j, c in enumerate(section):
            if int(c) > max_c:
                max_c = int(c)
                max_j = j + start

        best.append(max_c)
        start = max_j + 1

    result = 0
    for i, c in enumerate(best):
        result += c * (10 ** (digits - i - 1))

    return result


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    p1 = 0
    p2 = 0

    for line in lines:
        p1 += func(line)
        p2 += func(line, digits=12)

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == "__main__":
    main()
