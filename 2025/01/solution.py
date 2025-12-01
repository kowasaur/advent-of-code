position = 50


def func(line: str) -> tuple[int, int]:
    global position
    dir = line[0]
    val = int(line[1:])
    change = val if dir == "R" else -val

    new_position = position + change

    if new_position <= 0:
        # if position == 0 then it overcounts
        answer = (-new_position) // 100 + (position != 0)
    elif new_position >= 100:
        answer = (new_position - 100) // 100 + 1
    else:
        answer = 0

    position = new_position % 100

    return position == 0, answer


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    p1 = 0
    p2 = 0

    for line in lines:
        a1, a2 = func(line)
        p1 += a1
        p2 += a2

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == "__main__":
    main()
