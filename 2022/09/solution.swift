import Foundation

struct Position : Hashable {
    var x : Int // right is positive, left is negative
    var y : Int // up is positive, down is negative

    mutating func move(direction: String) {
        switch direction {
        case "U":
            y += 1
        case "D":
            y -= 1
        case "L":
            x -= 1
        case "R":
            x += 1
        default:
            break   
        }
    }

    mutating func move(toward: Position) {
        x += (toward.x - x).signum()
        y += (toward.y - y).signum()
    }

    func isNextTo(_ other: Position) -> Bool {
        return abs(x - other.x) <= 1 && abs(y - other.y) <= 1
    }
}

let file = try String(contentsOf: URL(fileURLWithPath: "input.txt"), encoding: .utf8)

func uniqueTailPositions(part: Int, rope_size: Int) {
    var head = Position(x: 0, y: 0)
    var rope = [Position](repeating: Position(x: 0, y: 0), count: rope_size - 1) // except head
    var tail_positions = [Position(x: 0, y: 0): true]

    for line in file.components(separatedBy: "\n") {
        let parts = line.components(separatedBy: " ")
        let steps = Int(parts[1])!

        for _ in 1...steps {
            head.move(direction: parts[0])
            var previous = head

            for (i, knot) in rope.enumerated() {
                if !knot.isNextTo(previous) {
                    rope[i].move(toward: previous)
                }
                previous = rope[i]
            }

            tail_positions[rope.last!] = true
        }
    }

    print("Part \(part):", tail_positions.values.count)
}

uniqueTailPositions(part: 1, rope_size: 2)
uniqueTailPositions(part: 2, rope_size: 10)
