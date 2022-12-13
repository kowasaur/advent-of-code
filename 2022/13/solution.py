def parse_packet_pairs(packet: str):
    left, right = packet.splitlines()
    return [eval(left), eval(right)]

def make_list(int_or_list):
    if type(int_or_list) is list: return int_or_list
    return [int_or_list]

def right_order(left, right):
    if type(left) is type(right) is int:
        if left == right: return "continue"
        return left < right
    
    left = make_list(left)
    right = make_list(right)
    zipped = zip(left, right)
    for l, r in zipped:
        comparison = right_order(l, r)
        if comparison == "continue": continue
        return comparison
    
    if len(left) == len(right): return "continue"
    return len(left) < len(right) 


with open("input.txt") as f:
    packet_pairs = [parse_packet_pairs(thing) for thing in f.read().split("\n\n")]

correct_sum = 0
for index, pair in enumerate(packet_pairs):
    if right_order(pair[0], pair[1]):
        correct_sum += index + 1

print("Part 1:", correct_sum)

all_packets = [[[2]], [[6]], *[packet for pair in packet_pairs for packet in pair]]

i = 0
j = 1
# Very inefficient but works
while True:
    left = all_packets[i]
    right = all_packets[j]
    if right_order(left, right):
        i += 1
        j += 1
    else:
        all_packets.insert(j, all_packets.pop(i))
        i = 0
        j = 1
    if j == len(all_packets): break

print("Part 2:", (all_packets.index([[2]]) + 1) * (all_packets.index([[6]]) + 1))
