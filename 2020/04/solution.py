Passport = dict[str, str]


def parseport(text: str) -> Passport:
    fields = [f.split(":") for f in text.replace("\n", " ").split()]
    return {k: v for k, v in fields}


def valid_fields(passport: Passport) -> bool:
    if len(passport) < 7:
        return False
    return len(passport) == 8 or passport.get("cid") is None


def valid_height(height: str) -> bool:
    if height[-2:] == "cm":
        return 150 <= int(height[:-2]) <= 193
    elif height[-2:] == "in":
        return 59 <= int(height[:-2]) <= 76
    return False


def valid_hair(colour: str) -> bool:
    return colour[0] == "#" and all(
        c.isdigit() or 'a' <= c <= 'f' for c in colour[1:])


def valid_values(passport: Passport) -> bool:
    if not valid_fields(passport):
        return False
    eye_colours = ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]
    return 1920 <= int(passport["byr"]) <= 2002 and 2010 <= int(
        passport["iyr"]) <= 2020 and 2020 <= int(
            passport["eyr"]) <= 2030 and valid_height(
                passport["hgt"]) and valid_hair(
                    passport["hcl"]) and passport["ecl"] in eye_colours and len(
                        passport["pid"]) == 9


def main():
    with open("./04/input.txt", "r") as f:
        passports = [parseport(p) for p in f.read().split("\n\n")]
        valid1 = sum(valid_fields(p) for p in passports)
        print("Part 1:", valid1)
        valid2 = sum(valid_values(p) for p in passports)
        print("Part 2:", valid2)


if __name__ == "__main__":
    main()
