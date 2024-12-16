from collections import defaultdict
import heapq

Vec2 = tuple[int, int]
DIRS = [(0, 1), (1, 0), (0, -1), (-1, 0)]


def in_bounds(pos: Vec2, lines: list[str]) -> bool:
    return 0 <= pos[0] < len(lines[0]) and 0 <= pos[1] < len(lines)


class Node:

    def __init__(self, pos: Vec2, cost: int, dir: Vec2, parent):
        self.pos = pos
        self.cost = cost
        self.dir = dir
        self.parent = parent

    def __lt__(self, other):
        return self.cost < other.cost


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    p1 = 1000000000

    startx = 0
    starty = 0

    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            if c == "S":
                startx = x
                starty = y
                break
        if startx != 0:
            break

    frontier = [Node((startx, starty), 0, (1, 0), None)]
    heapq.heapify(frontier)

    visited = {(startx, starty, 1, 0): 0}
    parents = defaultdict(list)
    goals = []

    while len(frontier) > 0:
        node = heapq.heappop(frontier)

        if lines[node.pos[1]][node.pos[0]] == "E":
            if node.cost < p1:
                goals = [(*node.pos, *node.dir)]
                p1 = node.cost
            elif node.cost == p1:
                goals.append((*node.pos, *node.dir))
                p1 = node.cost
            continue

        for dx, dy in DIRS:
            if (dx, dy) == node.dir:
                new_pos = (node.pos[0] + dx, node.pos[1] + dy)
                cost = node.cost + 1
            else:
                new_pos = node.pos
                cost = node.cost + 1000
            new_whole = (new_pos[0], new_pos[1], dx, dy)
            if in_bounds(new_pos,
                         lines) and lines[new_pos[1]][new_pos[0]] != "#" and (
                             new_whole not in visited or
                             visited[new_whole] >= cost):
                visited[new_whole] = cost
                heapq.heappush(frontier, Node(new_pos, cost, (dx, dy), node))
                if parents[new_whole] and visited[parents[new_whole]
                                                  [0]] > node.cost:
                    parents[new_whole] = [(*node.pos, *node.dir)]
                else:
                    parents[new_whole].append((*node.pos, *node.dir))

    print("Part 1:", p1)

    best_tiles = set()
    things = set(goals)
    while things:
        g = next(iter(things))
        things.remove(g)
        x, y, _, _ = g
        best_tiles.add((x, y))
        ps = parents[g]
        things = things.union(set(ps) - best_tiles)

    print("Part 2:", len(best_tiles))

    # Uncomment this to print the visual result of part 2
    # for y, line in enumerate(lines):
    #     for x, c in enumerate(line):
    #         if (x, y) in best_tiles:
    #             print("\033[91mO\033[0m", end="")
    #         else:
    #             print(c, end="")
    #     print()


if __name__ == '__main__':
    main()
