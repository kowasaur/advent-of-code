# Since Desmos can't parse text (I think) this has to be used
with open("input.txt") as f:
    numbers = [r[2:].split("..") for r in f.read()[13:].split(", ")]
print("L_x=" + numbers[0][0])
print("H_x=" + numbers[0][1])
print("L_y=" + numbers[1][0])
print("H_y=" + numbers[1][1])
