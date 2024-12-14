import re


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    width = 101
    height = 103

    q1 = q2 = q3 = q4 = 0

    robots = []

    for line in lines:
        px, py, vx, vy = map(int, re.findall(r"-?\d+", line))
        robots.append((px, py, vx, vy))
        px = (px + vx * 100) % width
        py = (py + vy * 100) % height

        if px == width // 2 or py == height // 2:
            continue

        if px < width // 2 and py < height // 2:
            q1 += 1
        elif px >= width // 2 and py < height // 2:
            q2 += 1
        elif px < width // 2 and py >= height // 2:
            q3 += 1
        else:
            q4 += 1

    print("Part 1:", q1 * q2 * q3 * q4)

    for i in range(10000):
        positions = set()
        for j in range(len(robots)):
            px, py, vx, vy = robots[j]
            robots[j] = ((px + vx) % width, (py + vy) % height, vx, vy)
            positions.add((px, py))

        if len(robots) == len(positions):
            print("Part 2:", i)
            for y in range(height):
                for x in range(width):
                    if (x, y) in positions:
                        print("#", end="")
                    else:
                        print(".", end="")
                print()
            break


if __name__ == '__main__':
    main()
