with open("input.txt", "r") as file:
    lines = file.readlines()

print(len(lines))

for line in lines:
    _, _, coords, size = line.split()
    x, y = map(int, coords[:-1].split(","))
    width, height = map(int, size.split("x"))
    print(x)
    print(y)
    print(width)
    print(height)
