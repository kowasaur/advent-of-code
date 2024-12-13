import re


def func(xa, ya, xb, yb, px, py) -> int:
    b = (xa * py - ya * px) / (xa * yb - ya * xb)
    a = (px - b * xb) / xa

    if a % 1 > 0.00001 or b % 1 > 0.00001 or a < 0 or b < 0:
        return 0

    return int(3 * a + b)


def main():
    with open("input.txt") as f:
        lines = f.read()[:-1].split("\n\n")

    p1 = 0
    p2 = 0

    for line in lines:
        l1, l2, l3 = line.split("\n")
        xa, ya = map(int, re.findall(r"\d+", l1))
        xb, yb = map(int, re.findall(r"\d+", l2))
        px, py = map(int, re.findall(r"\d+", l3))
        p1 += func(xa, ya, xb, yb, px, py)
        px += 10000000000000
        py += 10000000000000
        p2 += func(xa, ya, xb, yb, px, py)

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
