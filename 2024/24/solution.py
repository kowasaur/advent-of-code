def const0(a: str, b: str, values) -> bool:
    return False


def const1(a: str, b: str, values) -> bool:
    return True


ZERO = (const0, None, None)
ONE = (const1, None, None)


def andd(a: str, b: str, values) -> bool:
    f1, a1, b1 = values[a]
    result = f1(a1, b1, values)
    if result == False:
        return False
    f2, a2, b2 = values[b]
    return f2(a2, b2, values)


def orr(a: str, b: str, values) -> bool:
    f1, a1, b1 = values[a]
    result = f1(a1, b1, values)
    if result == True:
        return True
    f2, a2, b2 = values[b]
    return f2(a2, b2, values)


def xor(a: str, b: str, values) -> bool:
    f1, a1, b1 = values[a]
    result = f1(a1, b1, values)
    f2, a2, b2 = values[b]
    return result != f2(a2, b2, values)


def main():
    with open("input.txt") as f:
        lines = f.read().splitlines()

    values = {}
    connections = {}
    zs = []

    p1 = 0

    for line in lines:
        op = a = b = ""  # Ignore
        if ":" in line:
            k, z = line.split(": ")
            values[k] = ONE if z == "1" else ZERO
        elif "AND" in line:
            a, op, b, _, z = line.split(" ")
            values[z] = (andd, a, b)
        elif "XOR" in line:
            a, op, b, _, z = line.split(" ")
            values[z] = (xor, a, b)
        elif "OR" in line:
            a, op, b, _, z = line.split(" ")
            values[z] = (orr, a, b)
        else:
            continue
        if z.startswith("z"):
            zs.append(z)

        if op:
            connections[(op, min(a, b), max(a, b))] = z

    for z in zs:
        f, a, b = values[z]
        shift = int(z[1:])
        p1 += f(a, b, values) << shift

    print("Part 1:", p1)

    wires = {"c1": "bdj"}  # this isn't really used
    bits = len(zs)
    ps = set()
    ms = set()
    for i in range(1, bits - 1):
        p = connections[("XOR", f"x{i:02}", f"y{i:02}")]
        wires[f"p{i}"] = p
        m = connections[("AND", f"x{i:02}", f"y{i:02}")]
        wires[f"m{i}"] = m
        ps.add(p)
        ms.add(m)
        if p.startswith("z"):
            print(wires[f"p{i}"])
        if m.startswith("z"):
            print(wires[f"m{i}"])

    for (op, a, b), z in connections.items():
        if z in wires.values():
            continue
        if op == "XOR" and z != "z00" and (not z.startswith("z") or
                                           (a not in ps and b not in ps)):
            print(f"{z} = {a} {op} {b}", (a not in ps and b not in ps))
        elif op != "XOR" and z.startswith("z") and z != f"z{bits - 1}":
            print(f"{z} = {a} {op} {b}")
        elif op == "AND" and a not in ps and b not in ps:
            print(f"{z} = {a} {op} {b} ps")
        elif op == "OR" and a not in ms and b not in ms:
            print(f"{z} = {a} {op} {b} ms")

    # ended up not needing this
    # for i in range(1, bits - 1):
    #     print(i)
    #     px = wires[f"p{i}"]
    #     cx = wires[f"c{i}"]
    #     print(wires)
    #     hx = connections[("AND", min(px, cx), max(px, cx))]
    #     wires[f"h{i}"] = hx
    #     mx = wires[f"m{i}"]
    #     wires[f"c{i+1}"] = connections[("OR", min(mx, hx), max(mx, hx))]
    #
    # print(wires)


if __name__ == '__main__':
    main()
