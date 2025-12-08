from math import sqrt

Coord = tuple[int, int, int]


def main(input_file: str = "input.txt", pairs: int = 1000):
    with open(input_file) as f:
        lines = f.read().splitlines()

    boxes: list[Coord] = [tuple(int(x) for x in line.split(",")) for line in lines]

    circuits = {k: {k} for k in boxes}
    num_circuits = len(circuits)

    distances: list[tuple[float, Coord, Coord]] = []
    for i, b1 in enumerate(boxes):
        for b2 in boxes[i + 1 :]:
            dist = sqrt(
                (b1[0] - b2[0]) ** 2 + (b1[1] - b2[1]) ** 2 + (b1[2] - b2[2]) ** 2
            )
            distances.append((dist, b1, b2))
    distances.sort()

    def connect_shortest_pairs():
        nonlocal num_circuits, b1, b2
        _, b1, b2 = distances.pop(0)
        if circuits[b1] is circuits[b2]:
            return
        circuits[b1] |= circuits[b2]
        num_circuits -= 1
        for b in circuits[b2]:
            circuits[b] = circuits[b1]

    for _ in range(pairs):
        connect_shortest_pairs()

    unique_circuits = set(tuple(c) for c in circuits.values())

    circuit_sizes = sorted([len(c) for c in unique_circuits], reverse=True)

    print("Part 1:", circuit_sizes[0] * circuit_sizes[1] * circuit_sizes[2])

    while num_circuits > 1:
        connect_shortest_pairs()

    print("Part 2:", b1[0] * b2[0])


if __name__ == "__main__":
    main()
