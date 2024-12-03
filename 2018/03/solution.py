# This is the python equivalent of solution.lol

WIDTH = 1000
HEIGHT = 1000
TOTAL = WIDTH * HEIGHT

counts = {}

for i in range(TOTAL):
    counts[i] = 0

lines = int(input())

overlapping = 0

for _ in range(lines):
    initx = int(input())
    inity = int(input())
    w = int(input())
    h = int(input())

    for y in range(h):
        for x in range(w):
            realx = x + initx
            realy = y + inity
            ind = realx + realy * WIDTH
            old = counts[ind]

            if old == 0:
                counts[ind] = 1
            elif old == 1:
                counts[ind] = 2
                overlapping += 1

print("Part 1:", overlapping)
