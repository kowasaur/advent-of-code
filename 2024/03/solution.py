import re


def main():
    with open("input.txt") as f:
        file = f.read()

    things = re.findall(r"mul\(\d+,\d+\)|do\(\)|don't\(\)", file)

    p1 = 0
    p2 = 0
    do = True
    for thing in things:
        if thing == "do()":
            do = True
        elif thing == "don't()":
            do = False
        else:
            x, y = thing.split("(")[1].split(")")[0].split(",")
            if do:
                p2 += int(x) * int(y)
            p1 += int(x) * int(y)

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
