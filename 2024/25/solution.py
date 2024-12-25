def main():
    with open("input.txt") as f:
        things = f.read()[:-1].split("\n\n")

    p1 = 0
    locks = []
    keys = []

    for thing in things:
        rows = thing.split("\n")
        if all(x == "#" for x in rows[0]):
            # lock
            locks.append([sum(x[i] == "#" for x in rows) - 1 for i in range(5)])
        else:
            # key
            keys.append([sum(x[i] == "#" for x in rows) - 1 for i in range(5)])

    for lock in locks:
        for key in keys:
            if all(5 - l >= k for l, k in zip(lock, key)):
                p1 += 1

    print("Part 1:", p1)


if __name__ == '__main__':
    main()
