def calibration(line: str) -> int:
    aim, values = line.split(": ")
    aim = int(aim)
    values = list(map(int, values.split()))

    bits = (len(values) - 1)

    for ops in range(1 << bits):
        v = values[0]
        for i in range(bits):
            if ops & (1 << i):
                v += values[i + 1]
            else:
                v *= values[i + 1]

        if v == aim:
            return aim

    return 0


def calibration2(line: str) -> int:
    aim, values = line.split(": ")
    aim = int(aim)
    values = list(map(int, values.split()))

    bits = (len(values) - 1)

    for ops in range(3**bits):
        v = values[0]
        for i in range(bits):
            x = (ops // 3**i) % 3
            if x == 0:
                v += values[i + 1]
            elif x == 1:
                v = int(str(v) + str(values[i + 1]))
            else:
                v *= values[i + 1]

        if v == aim:
            return aim

    return 0


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    p1 = 0
    p2 = 0

    for line in lines:
        x = calibration(line)
        p1 += x
        if x == 0:
            x = calibration2(line)
        p2 += x

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
