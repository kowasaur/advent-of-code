import re


def run(a: int, program: list[int]) -> list[int]:
    b = c = i = 0

    def combo() -> int:
        d = program[i + 1]
        if 0 <= d <= 3:
            return d
        if d == 4:
            return a
        if d == 5:
            return b
        if d == 6:
            return c
        raise ValueError

    out = []

    while i < len(program):
        op = program[i]
        match op:
            case 0:
                a //= 2**combo()
            case 1:
                b ^= program[i + 1]
            case 2:
                b = combo() % 8
            case 3:
                if a != 0:
                    i = program[i + 1]
                    continue
            case 4:
                b ^= c
            case 5:
                out.append(combo() % 8)
            case 6:
                b = a // 2**combo()
            case 7:
                c = a // 2**combo()
        i += 2

    return out


def satisfies_constraint(i: int, num: int, p2: int) -> bool:
    a = p2 >> (i * 3)
    b = a % 8
    return num == (b ^ 5 ^ (a >> (b ^ 2)) % 8)


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    a = int(re.findall(r"\d+", lines[0])[0])

    program = list(map(int, re.findall(r"\d+", lines[4])))

    out = run(a, program)

    print("Part 1:", ",".join(map(str, out)))

    p2 = 0
    i = len(program) - 1
    added = 0

    while i >= 0:
        num = program[i]
        while not satisfies_constraint(i, num, p2):
            p2 += 1 << (i * 3)
            added += 1
            if added == 8:
                added = 0
                i = len(program)
                break
        i -= 1

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
