def main():
    with open("input.txt") as f:
        lines = f.readlines()

    parts = [line.split("   ") for line in lines]
    together = [(int(x), int(y)) for x, y in parts]

    first, second = list(zip(*together))

    first = sorted(first)
    second = sorted(second)

    p1 = sum(abs(f - s) for f, s in zip(first, second))

    print("Part 1:", p1)

    counts = {}
    for s in second:
        counts[s] = counts.get(s, 0) + 1

    p2 = sum(f * counts.get(f, 0) for f in first)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
