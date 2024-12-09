def main():
    with open("input.txt") as f:
        file = f.read()[:-1]

    ids = []

    for i, c in enumerate(file):
        if i % 2 == 0:
            ids.append((i // 2, int(c)))
        else:
            ids.append((None, int(c)))

    ids2 = ids.copy()

    left = 1

    while left < len(ids):
        id, amount = ids.pop()
        ids.pop()
        _, lamount = ids[left]

        while lamount < amount:
            ids[left] = id, lamount
            amount -= lamount
            left += 2
            if left >= len(ids):
                ids.append((id, amount))
                lamount = 0
                left += 2
                break
            _, lamount = ids[left]

        if lamount == amount:
            ids[left] = id, amount
            left += 2
        elif lamount > amount:
            ids[left] = id, amount
            ids.insert(left + 1, (None, lamount - amount))
            left += 1

    i = 0
    p1 = 0

    for id, amount in ids:
        for _ in range(amount):
            p1 += i * id
            i += 1

    print("Part 1:", p1)

    p2 = 0

    right = len(ids2) - 1
    while right > 1:
        id, amount = ids2[right]
        if id is None:
            right -= 1
            continue

        for i in range(1, right):
            lid, lamount = ids2[i]
            if lid is not None:
                continue
            if lamount == amount:
                ids2[i] = id, amount
                ids2[right] = None, amount
                break
            elif lamount > amount:
                ids2[right] = None, amount
                ids2[i] = id, amount
                ids2.insert(i + 1, (None, lamount - amount))
                right += 1
                break
        right -= 1

    i = 0

    for id, amount in ids2:
        if id is None:
            i += amount
            continue
        for _ in range(amount):
            p2 += i * id
            i += 1

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
