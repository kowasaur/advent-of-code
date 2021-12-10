with open("input.txt") as f:
    lines = f.readlines()

digits = [d for line in lines for d in line.split("|")[1].split()]
valid = sum([len(d) in [2,4,3,7] for d in digits])
print(valid)
