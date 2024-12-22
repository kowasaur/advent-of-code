from collections import defaultdict, deque


def next_secret(n: int) -> int:
    n ^= n * 64 % 16777216
    n ^= n // 32 % 16777216
    n ^= n * 2048 % 16777216
    return n


def func(line: str) -> tuple[int, dict[tuple, int]]:
    n = int(line)
    p = n % 10
    prices = {}
    window = deque(maxlen=4)

    for _ in range(4):
        n = next_secret(n)
        np = n % 10
        window.append(p - np)
        p = np

    for _ in range(1996):
        t = tuple(window)
        if t not in prices:
            prices[t] = p

        n = next_secret(n)
        np = n % 10
        window.popleft()
        window.append(p - np)
        p = np

    t = tuple(window)
    if t not in prices:
        prices[t] = p

    return n, prices


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    p1 = 0
    total = defaultdict(int)

    for line in lines:
        thing, prices = func(line)
        p1 += thing
        for k, v in prices.items():
            total[k] += v

    print("Part 1:", p1)
    print("Part 2:", max(total[k] for k in total))


if __name__ == '__main__':
    main()
