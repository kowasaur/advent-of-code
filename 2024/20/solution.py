import heapq

Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


def in_bounds(pos: Vec2, lines: list[str]) -> bool:
    return 0 <= pos[0] < len(lines[0]) and 0 <= pos[1] < len(lines)


def dijkstra(start: Vec2, lines: list[str]) -> dict[Vec2, int]:
    frontier = [(0, start)]
    heapq.heapify(frontier)
    distances = {start: 0}

    while frontier:
        dist, pos = heapq.heappop(frontier)

        for dx, dy in DIRS:
            new_pos = pos[0] + dx, pos[1] + dy
            if in_bounds(new_pos, lines) and new_pos not in distances and lines[
                    new_pos[1]][new_pos[0]] != "#":
                distances[new_pos] = dist + 1
                heapq.heappush(frontier, (dist + 1, new_pos))

    return distances


def cheats(start: Vec2, lines: list[str], distances: dict[Vec2, int],
           limit: int) -> int:
    visited = {start}
    frontier = [start]
    result = 0

    for i in range(limit + 1):
        new_frontier = []
        for pos in frontier:
            if pos in distances and distances[start] - (i +
                                                        distances[pos]) >= 100:
                result += 1

            for dx, dy in DIRS:
                nx, ny = pos[0] + dx, pos[1] + dy
                if in_bounds((nx, ny), lines) and (nx, ny) not in visited:
                    visited.add((nx, ny))
                    new_frontier.append((nx, ny))

        frontier = new_frontier

    return result


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    start = end = 0, 0
    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            if c == "S":
                start = (x, y)
            elif c == "E":
                end = (x, y)

    reachable_from_start = dijkstra(start, lines)
    distances_to_end = dijkstra(end, lines)

    p1 = 0
    p2 = 0

    for x, y in reachable_from_start:
        p1 += cheats((x, y), lines, distances_to_end, 2)
        p2 += cheats((x, y), lines, distances_to_end, 20)

    print("Part 1:", p1)
    print("Part 2:", p2)


if __name__ == '__main__':
    main()
