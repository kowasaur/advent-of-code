def middlenum(nums: list[int], rule: dict[int, list[int]]) -> int:
    for i, n in enumerate(nums):
        for n2 in nums[i + 1:]:
            if n2 in rule and n in rule[n2]:
                return 0

    return nums[len(nums) // 2]


def sort(nums: list[int], rule: dict[int, list[int]]) -> list[int]:
    swap = True
    while swap:
        swap = False
        for i in range(len(nums) - 1):
            if nums[i] in rule and nums[i + 1] in rule[nums[i]]:
                nums[i], nums[i + 1] = nums[i + 1], nums[i]
                swap = True

    return nums


def main():
    with open("input.txt") as f:
        file = f.read()

    first, second = file.split("\n\n")

    strs = [x.split("|") for x in first.split("\n")]

    rule = {}
    for s in strs:
        k = int(s[0])
        v = int(s[1])
        if k not in rule:
            rule[k] = []
        rule[k].append(v)

    p1 = 0
    bad = []

    for line in second.split("\n")[:-1]:
        nums = [int(x) for x in line.split(",")]
        y = middlenum(nums, rule)
        p1 += y
        if y == 0:
            bad.append(nums)

    print("Part 1:", p1)

    p2 = 0
    for b in bad:
        s = sort(b, rule)
        p2 += s[len(s) // 2]

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
