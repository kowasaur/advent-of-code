def increasing(nums):
    for i in range(1, len(nums)):
        if nums[i] <= nums[i - 1] or nums[i] - nums[i - 1] > 3:
            return False
    return True


def decreasing(nums):
    for i in range(1, len(nums)):
        if nums[i] >= nums[i - 1] or nums[i - 1] - nums[i] > 3:
            return False
    return True


def increasing2(nums):
    for i in range(1, len(nums)):
        if nums[i] <= nums[i - 1] or nums[i] - nums[i - 1] > 3:
            return i
    return -1


def decreasing2(nums):
    for i in range(1, len(nums)):
        if nums[i] >= nums[i - 1] or nums[i - 1] - nums[i] > 3:
            return i
    return -1


def main():
    with open("input.txt") as f:
        lines = f.readlines()

    p1 = 0
    p2 = 0
    for line in lines:
        nums = list(map(int, line.split()))
        if increasing(nums) or decreasing(nums):
            p1 += 1
            p2 += 1
        else:
            i = increasing2(nums)
            j = decreasing2(nums)
            thing = True
            if i > -1:
                num2 = nums.copy()
                num3 = nums.copy()
                num2.pop(i)
                num3.pop(i - 1)
                if increasing(num2) or decreasing(num2) or increasing(
                        num3) or decreasing(num3):
                    p2 += 1
                    thing = False
            if j > -1 and thing:
                num2 = nums.copy()
                num3 = nums.copy()
                num2.pop(j)
                num3.pop(j - 1)
                if increasing(num2) or decreasing(num2) or increasing(
                        num3) or decreasing(num3):
                    p2 += 1

    print("Part 1:", p1)

    print("Part 2:", p2)


if __name__ == '__main__':
    main()
