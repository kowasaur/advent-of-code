def folds_all_same(s: str, folds: int) -> bool:
    if len(s) % folds != 0:
        return False
    first = len(s) // folds
    for i in range(1, folds):
        if s[i * first : (i + 1) * first] != s[:first]:
            return False
    return True


def func1(line: str) -> int:
    start, end = [int(x) for x in line.split("-")]
    answer = 0
    for i in range(start, end + 1):
        s = str(i)
        if s[: len(s) // 2] == s[len(s) // 2 :]:
            answer += i
    return answer


def func2(line: str) -> int:
    start, end = [int(x) for x in line.split("-")]
    answer = 0
    for i in range(start, end + 1):
        s = str(i)
        for j in range(2, len(s) + 1):
            if folds_all_same(s, j):
                answer += i
                break
    return answer


def main():
    with open("input.txt") as f:
        lines = f.read().split(",")

    p1 = 0
    p2 = 0

    for line in lines:
        p1 += func1(line)
        p2 += func2(line)

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == "__main__":
    main()
