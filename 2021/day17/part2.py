target = {}
with open("input.txt") as f:
    numbers = [r[2:].split("..") for r in f.read()[13:].split(", ")]
    target["lx"] = int(numbers[0][0])
    target["hx"] = int(numbers[0][1])
    target["ly"] = int(numbers[1][0])
    target["hy"] = int(numbers[1][1])

def in_target(x_vel: int, y_vel: int):
    x = 0
    y = 0
    while x <= target["hx"] and y >= target["ly"]:
        x += x_vel
        y += y_vel
        if target["lx"] <= x <= target["hx"] and target["ly"] <= y <= target["hy"]:
            return True
        x_vel = max(x_vel - 1, 0)
        y_vel -= 1
    return False

valid_count = 0

for x in range(1, target["hx"] + 1):
    for y in range(target["ly"], target["ly"] * -1):
        valid_count += int(in_target(x, y))

print(valid_count)
