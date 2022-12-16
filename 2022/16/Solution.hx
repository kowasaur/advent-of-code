import sys.io.File;

typedef Valve = { flow_rate: Int, tunnels: Array<String> };
typedef Node = { 
    pressure: Int, 
    open: Map<String, Bool>, 
    dont_revisit: Map<String, Bool>, 
    previous: String, 
    current: String
};

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

    var minutes_left = 30;
    var nodes: Array<Node> = [{
        pressure: 0, 
        open: new Map<String, Bool>(), 
        dont_revisit: new Map<String, Bool>(), 
        previous: null, 
        current: "AA"
    }];

    while (minutes_left > 0) {
        minutes_left--;
        var new_nodes = new Array<Node>();

        for (node in nodes) {
            var self = node.current;
            var valve = valves[self];
            var tunnels = valve.tunnels.filter(t -> !node.dont_revisit[t]);

            if (valve.flow_rate != 0 && !node.open.exists(self)) {
                var new_open = node.open.copy();
                new_open[self] = true;

                var new_dont_revisit = node.dont_revisit;
                if (tunnels.length == 1) {
                    new_dont_revisit = new_dont_revisit.copy();
                    new_dont_revisit[self] = true;
                }

                new_nodes.push({
                    pressure: node.pressure + valve.flow_rate * minutes_left, 
                    open: new_open, 
                    dont_revisit: new_dont_revisit, 
                    previous: null, 
                    current: self
                });
            }

            for (tunnel in tunnels) {
                if (tunnel == node.previous) continue;
                new_nodes.push({pressure: node.pressure, open: node.open, dont_revisit: node.dont_revisit, previous: node.current, current: tunnel});
            }
        }

        nodes = new_nodes;
        // Remove the worst half
        if (minutes_left < 28 && (minutes_left+1) % 2 == 0) {
            nodes.sort((a, b) -> a.pressure - b.pressure);
            nodes = nodes.slice(Math.floor(nodes.length / 2));
        }
    }

    Sys.println("Part 1: " + Lambda.fold(nodes, (n, result) -> Math.max(n.pressure, result), 0));

}
