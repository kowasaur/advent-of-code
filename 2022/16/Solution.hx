import sys.io.File;

typedef Valve = { flow_rate: Int, tunnels: Array<String> };
typedef Valves = Map<String, Valve>;

class Position {
    public var current: String;
    public var previous: String = null;

    public function new(current: String) {
        this.current = current;
    }

    public function to(new_current: String) {
        var n = new Position(new_current);
        n.previous = current;
        return n;
    }
}

class Node {
    public var pressure: Int;
    var open: Map<String, Bool>;
    var position: Position;

    public function new(pressure: Int, open: Map<String, Bool>, position: Position) {
        this.pressure = pressure;
        this.open = open;
        this.position = position;
    }

    function openValve(valve: Valve, pos: Position, minutes_left: Int) {
        if (valve.flow_rate == 0 || open.exists(pos.current)) return null;
        var new_open = open.copy();
        new_open[pos.current] = true;
        return new Node(pressure + valve.flow_rate * minutes_left, new_open, new Position(pos.current));
    }

    public function nextMinuteNodes(new_nodes: Array<Node>, valves: Valves, minutes_left: Int) {
        var valve = valves[position.current];

        var open_node = openValve(valve, position, minutes_left);
        if (open_node != null) new_nodes.push(open_node);

        for (tunnel in valve.tunnels) {
            if (tunnel == position.previous) continue;
            new_nodes.push(new Node(pressure, open, position.to(tunnel)));
        }
    }
}

class NodePart2 extends Node {
    var elephant: Position;

    public function new(pressure: Int, open: Map<String, Bool>, position: Position, elephant: Position) {
        super(pressure, open, position);
        this.elephant = elephant;
    }

    public override function nextMinuteNodes(new_nodes: Array<Node>, valves: Valves, minutes_left: Int) {
        var valve = valves[position.current];
        var evalve = valves[elephant.current];

        var eopen_node = openValve(evalve, elephant, minutes_left);
        if (eopen_node != null) {
            var open_node = eopen_node.openValve(valve, position, minutes_left);
            if (open_node != null) {
                new_nodes.push(new NodePart2(open_node.pressure, open_node.open, open_node.position, eopen_node.position));
            }

            for (tunnel in valve.tunnels) {
                if (tunnel == position.previous) continue;
                new_nodes.push(new NodePart2(eopen_node.pressure, eopen_node.open, position.to(tunnel), eopen_node.position));
            }
        }

        for (etunnel in evalve.tunnels) {
            if (etunnel == elephant.previous) continue;
            var new_ele = elephant.to(etunnel);

            var open_node = openValve(valve, position, minutes_left);
            if (open_node != null) {
                new_nodes.push(new NodePart2(open_node.pressure, open_node.open, open_node.position, new_ele));
            }

            for (tunnel in valve.tunnels) {
                if (tunnel == position.previous) continue;
                new_nodes.push(new NodePart2(pressure, open, position.to(tunnel), new_ele));
            }
        }
    }
}

function mostPressure(part: Int, valves: Valves, minutes_left: Int, first_node: Node, cull: Float) {
    var nodes = [first_node];
    var start_culling = minutes_left - 2;

    while (minutes_left > 0) {
        trace(minutes_left);
        minutes_left--;
        var new_nodes = new Array<Node>();

        for (node in nodes) node.nextMinuteNodes(new_nodes, valves, minutes_left);
        
        nodes = new_nodes;
        // Remove the worst half
        if (minutes_left < start_culling && (minutes_left+1) % 2 == 0) {
            nodes.sort((a, b) -> a.pressure - b.pressure);
            nodes = nodes.slice(Math.floor(nodes.length * cull));
        }
    }

    Sys.println('Part $part: ' + Lambda.fold(nodes, (n, result) -> Math.max(n.pressure, result), 0));
}

function main() {
    var file = File.getContent("input.txt").split("\n");
    var valves = new Map<String, Valve>();

    for (line in file) {
        var parts = line.split(" ");
        var flow = parts[4];
        var flow_rate = Std.parseInt(flow.substring(5, flow.length - 1));
        var name = parts[1];
        var tunnels = parts.slice(9).map(t -> t.charAt(t.length-1) == "," ? t.substring(0, t.length-1) : t);
        valves[name] = { flow_rate: flow_rate, tunnels: tunnels };
    }

    var empty_string_set = new Map<String, Bool>();
    var initial_position = new Position("AA");
    mostPressure(1, valves, 30, new Node(0, empty_string_set, initial_position), 0.5);
    mostPressure(2, valves, 26, new NodePart2(0, empty_string_set, initial_position, initial_position), 0.75);
}
