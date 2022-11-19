import sys

def parse_entry(entry: str):
    policy, password = entry.split(": ")
    rang, char = policy.split(" ")
    lower, upper = rang.split("-")
    return {
        "password": password,
        "char": char,
        "lower": int(lower),
        "upper": int(upper)
    }

def part1_is_valid_entry(entry):
    return entry["lower"] <= entry["password"].count(entry["char"]) <= entry["upper"]

def part2_is_valid_entry(entry):
    pas = entry["password"]
    c = entry["char"]
    return (pas[entry["lower"]-1] == c) ^ (pas[entry["upper"]-1] == c)

def count_valid_entries(is_valid_entry):
    valid_passwords = 0
    for e in entries:
        if is_valid_entry(e):
            valid_passwords += 1
    print(valid_passwords)

if __name__ == "__main__":
    with open("./02/input.txt") as f:
        entries = [parse_entry(e) for e in f.read().splitlines()]
    if sys.argv[1] == '1':
        count_valid_entries(part1_is_valid_entry)
    elif sys.argv[1] == '2':
        count_valid_entries(part2_is_valid_entry)
