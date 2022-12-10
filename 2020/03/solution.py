with open("./03/input.txt") as f:
    file = f.read().splitlines()

def tree_count(right: int, down2 = False):
    trees = enumerate(file)
    if down2: trees = filter(lambda il: il[0] % 2 == 0, trees)
    return sum([int(line[int(index * right) % len(line)] == "#") for index, line in trees])

r1d1 = tree_count(1)
r3d1 = tree_count(3)
r5d1 = tree_count(5)
r7d1 = tree_count(7)
r1d2 = tree_count(0.5, down2=True)

print("Part 1:", r3d1)
print("Part 2:", r1d1 * r3d1 * r5d1 * r7d1 * r1d2)
