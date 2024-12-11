def blink(stones: dict[int, int]) -> dict[int, int]:
    new_stones: dict[int, int] = {}

    for stone, amount in stones.items():
        if stone == 0:
            v = new_stones.get(1, 0)
            new_stones[1] = v + amount
        elif len(str(stone)) % 2 == 0:
            sstone = str(stone)
            middle = len(sstone) // 2
            kl = int(sstone[:middle])
            vl = new_stones.get(kl, 0)
            new_stones[kl] = vl + amount
            kr = int(sstone[middle:])
            vr = new_stones.get(kr, 0)
            new_stones[kr] = vr + amount
        else:
            v = new_stones.get(stone * 2024, 0)
            new_stones[stone * 2024] = v + amount

    return new_stones


def main():
    with open("input.txt") as f:
        file = f.read().split(" ")

    stones = {int(x): 1 for x in file}

    for _ in range(25):
        stones = blink(stones)

    print("Part 1:", sum(stones.values()))

    for _ in range(50):
        stones = blink(stones)

    print("Part 2:", sum(stones.values()))


if __name__ == '__main__':
    main()
