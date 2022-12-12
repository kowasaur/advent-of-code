import 'dart:io';
import 'dart:math';

typedef Hill = List<List<int>>;

class Pair {
  final int x;
  final int y;

  const Pair(this.x, this.y);

  int value(Hill file) {
    final value = file[y][x];
    return value == S ? a : value;
  }

  bool canClimb(Hill file, Pair to, List<Pair> path) {
    final to_height = (to.value(file) == E ? z : to.value(file));
    return !path.contains(to) && to_height <= value(file) + 1;
  }

  @override
  bool operator ==(covariant Pair other) => x == other.x && y == other.y;

  @override
  int get hashCode => (x + y) * (x + y + 1) ~/ 2 + x; // Cantor pairing function

  @override
  String toString() => "(${x}, ${y})";
}

const E = 69;
const S = 83;
const a = 97;
const z = 122;

final length_to_end = <Pair, Pair>{};

int shortestPathLength(Hill file, List<Pair> path, Pair new_coord) {
  if (new_coord.value(file) == E) return path.length;
  if (length_to_end.containsKey(new_coord)) {
    final cache = length_to_end[new_coord]!;
    if (cache.y <= path.length) return cache.x + path.length;
  }

  final new_path = [...path, new_coord];
  int shortest_length = 100000;

  final left = Pair(new_coord.x - 1, new_coord.y);
  if (left.x >= 0 && new_coord.canClimb(file, left, path))
    shortest_length =
        min(shortest_length, shortestPathLength(file, new_path, left));

  final right = Pair(new_coord.x + 1, new_coord.y);
  if (right.x < file[0].length && new_coord.canClimb(file, right, path))
    shortest_length =
        min(shortest_length, shortestPathLength(file, new_path, right));

  final up = Pair(new_coord.x, new_coord.y - 1);
  if (up.y >= 0 && new_coord.canClimb(file, up, path))
    shortest_length =
        min(shortest_length, shortestPathLength(file, new_path, up));

  final down = Pair(new_coord.x, new_coord.y + 1);
  if (down.y < file.length && new_coord.canClimb(file, down, path))
    shortest_length =
        min(shortest_length, shortestPathLength(file, new_path, down));

  length_to_end[new_coord] = Pair(shortest_length - path.length, path.length);

  return shortest_length;
}

void main() {
  final Hill file = File("./input.txt")
      .readAsLinesSync()
      .map((line) => line.runes.map((e) => e).toList())
      .toList();
  final start =
      Pair(0, file.indexWhere((line) => line.contains('S'.codeUnits[0])));

  int shortest = shortestPathLength(file, <Pair>[], start);
  print("Part 1: ${shortest}");

  for (var y = 0; y < file.length; y++) {
    for (var x = 0; x < file[0].length; x++) {
      final point = Pair(x, y);
      if (point.value(file) == a)
        shortest = min(shortest, shortestPathLength(file, <Pair>[], point));
    }
  }
  print("Part 2: ${shortest}");
}
