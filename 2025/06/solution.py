from functools import reduce
from operator import mul, concat


def problem(lines: list[str], start: int):
    ops = lines[-1]
    op = ops[start]

    end = start + 1
    while end < len(ops) and ops[end] == " ":
        end += 1

    answer = 1 if op == "*" else 0
    rows = lines[:-1]
    for i in range(start, end - (end < len(ops))):
        num = int(reduce(concat, (r[i] for r in rows), ""))
        if op == "*":
            answer *= num
        else:
            answer += num

    return answer, end


def main(input_file: str = "input.txt"):
    with open(input_file) as f:
        lines = f.read().splitlines()

    ops = lines[-1].split()
    rows = [[int(x) for x in line.split()] for line in lines[:-1]]

    p1 = 0
    for i, op in enumerate(ops):
        if op == "*":
            p1 += reduce(mul, (r[i] for r in rows), 1)
        else:
            p1 += sum(r[i] for r in rows)
    print("Part 1:", p1)

    p2 = 0
    i = 0
    while i < len(lines[-1]):
        ans, i = problem(lines, i)
        p2 += ans
    print("Part 2:", p2)


if __name__ == "__main__":
    main()
