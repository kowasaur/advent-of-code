class Ranges:
    def __init__(self, range: tuple[int, int]):
        self.ranges = [range]

    def size(self) -> int:
        return sum(e - s + 1 for s, e in self.ranges)

    def add(self, range: tuple[int, int]):
        self._add(range)
        self._merge()

    def _add(self, range: tuple[int, int]):
        ns, ne = range
        for i, (s, e) in enumerate(self.ranges):
            if ns <= s:
                if ne < s:
                    self.ranges.insert(i, (ns, ne))
                    return
                self.ranges[i] = (ns, max(ne, e))
                return
        if ns > self.ranges[-1][1]:
            self.ranges.append((ns, ne))
            return
        self.ranges[-1] = (self.ranges[-1][0], max(ne, self.ranges[-1][1]))

    def _merge(self):
        merged = self.ranges[:1]
        for ns, ne in self.ranges[1:]:
            s, e = merged[-1]
            if ns <= e + 1:
                merged[-1] = (s, max(e, ne))
            else:
                merged.append((ns, ne))
        self.ranges = merged

    def __repr__(self):
        return repr(self.ranges)


def main(input_file: str = "input.txt"):
    with open(input_file) as f:
        ranges_str, ingredients_str = f.read().split("\n\n")
    ranges: list[tuple[int, int]] = [
        tuple(int(x) for x in line.split("-")) for line in ranges_str.splitlines()
    ]
    ingredients = [int(x) for x in ingredients_str.splitlines()]

    p1 = 0
    for ing in ingredients:
        p1 += any(r[0] <= ing <= r[1] for r in ranges)

    print("Part 1:", p1)

    all_fresh = Ranges(ranges[0])
    for r in ranges[1:]:
        all_fresh.add(r)

    print("Part 2:", all_fresh.size())


if __name__ == "__main__":
    main()
