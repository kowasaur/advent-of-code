# Return string in alphabetical order
def sort(s):
    return "".join(sorted(s))

def sub_chars(str1, str2):
    return "".join([c for c in str1 if c not in str2])

def add_chars(str1, str2):
    return sort(str1 + sub_chars(str2, str1))

LENGTHS = {
    2: 1,
    4: 4,
    3: 7,
    7: 8
}

def decode_entry(e: list[list[str]]):
    entry = e[0]
    digits = {LENGTHS[len(d)]:d for d in entry if len(d) in LENGTHS.keys()}

    c5 = [d for d in entry if len(d) == 5] # 2, 3, 5
    for i, c in enumerate(c5):
        if len(add_chars(c, c5[(i+1) % 3])) == 7:
            digits[3] = c5[(i+2) % 3]

    digits[9] = add_chars(digits[3], digits[4])

    top = sub_chars(digits[7], digits[1])
    top_middle_bottom = sub_chars(digits[3], digits[1])
    bottom = sub_chars(sub_chars(digits[9], digits[4]), top)
    middle = sub_chars(sub_chars(top_middle_bottom, top), bottom)

    digits[0] = sub_chars(digits[8], middle)
    digits[6] = [d for d in entry if len(d) == 6 and d not in digits.values()][0]

    bottom_botleft = sub_chars(sub_chars(digits[8], digits[4]), top)
    botleft = sub_chars(bottom_botleft, bottom)

    digits[5] = sub_chars(digits[6], botleft)
    digits[2] = [d for d in entry if d not in digits.values()][0]

    inverse_digits = { y:str(x) for x,y in digits.items() } # swap values and keys and make x string
    return int("".join(inverse_digits[d] for d in e[1]))


with open("input.txt") as f:
    lines = f.readlines()

entries = [[[sort(d) for d in digits.split()] for digits in line.split("|")] for line in lines]

answer = sum([decode_entry(ent) for ent in entries])

print(answer)
