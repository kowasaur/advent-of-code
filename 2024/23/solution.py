from collections import defaultdict


def cliques(graph: defaultdict[str, list[str]], interconnected: set[tuple]):
    result = set()

    for sm_clique in interconnected:
        n1 = sm_clique[0]
        maybe = set(graph[n1]) - set(sm_clique)
        for n2 in maybe:
            for n3 in sm_clique[1:]:
                if n2 not in graph[n3]:
                    break
            else:
                result.add(tuple(sorted(list(sm_clique) + [n2])))

    return result


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    graph = defaultdict(list)

    for line in lines:
        start, end = line.split("-")
        graph[start].append(end)
        graph[end].append(start)

    interconnected: set[tuple[str, str, str]] = set()

    for node in graph:
        ns = graph[node]
        for n2 in ns:
            for n3 in ns:
                if n2 != n3 and n2 in graph[n3]:
                    interconnected.add(tuple(sorted((node, n2, n3))))

    print("Part 1:",
          len([x for x in interconnected if any([y[0] == "t" for y in x])]))

    while len(interconnected) > 1:
        interconnected = cliques(graph, interconnected)

    print("Part 2:", ",".join(interconnected.pop()))


if __name__ == '__main__':
    main()
