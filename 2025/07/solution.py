from collections import defaultdict


def main(input_file: str = "input.txt"):
    with open(input_file) as f:
        lines = f.read().splitlines()

    p1 = 0

    beams = defaultdict()
    beams[lines[0].index("S")] = 1

    for line in lines[1:]:
        new_beams: dict[int, int] = defaultdict(int)
        for beam in beams:
            if line[beam] == "^":
                if beam > 0:
                    new_beams[beam - 1] += beams[beam]
                if beam < len(line) - 1:
                    new_beams[beam + 1] += beams[beam]
                p1 += 1
            else:
                new_beams[beam] += beams[beam]
        beams = new_beams

    print("Part 1:", p1)
    print("Part 2:", sum(beams.values()))


if __name__ == "__main__":
    main()
