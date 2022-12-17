import sys.io.File;

typedef Valve = { flow_rate: Int, tunnels: Array<String> };
typedef Node = { 
    pressure: Int, 
    open: Map<String, Bool>, 
    previous: String, 
    current: String,
    eprevious: String,
    ecurrent: String
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

    var minutes_left = 26;
    var nodes: Array<Node> = [{
        pressure: 0, 
        open: new Map<String, Bool>(), 
        previous: null, 
        current: "AA",
        eprevious: null,
        ecurrent: "AA"
    }];

    while (minutes_left > 0) {
        trace(minutes_left);
        minutes_left--;
        var new_nodes = new Array<Node>();

        for (node in nodes) {
            var self = node.current;
            var valve = valves[self];
            var tunnels = valve.tunnels;

            var elephant = node.ecurrent;
            var evalve = valves[elephant];

            if (evalve.flow_rate != 0 && !node.open.exists(elephant)) {
                var new_open = node.open.copy();
                new_open[elephant] = true;

                if (valve.flow_rate != 0 && !new_open.exists(self)) {
                    new_open[self] = true;
                    new_nodes.push({
                        pressure: node.pressure + (valve.flow_rate + evalve.flow_rate) * minutes_left, 
                        open: new_open, 
                        previous: null, 
                        current: self,
                        eprevious: null,
                        ecurrent: elephant
                    });
                }

                for (tunnel in tunnels) {
                    if (tunnel == node.previous) continue;
                    new_nodes.push({
                        pressure: node.pressure + evalve.flow_rate * minutes_left, 
                        open: new_open, 
                        previous: node.current, 
                        current: tunnel,
                        eprevious: null,
                        ecurrent: elephant
                    });
                }
            }

            for (etunnel in evalve.tunnels) {
                if (etunnel == node.eprevious) continue;

                if (valve.flow_rate != 0 && !node.open.exists(self)) {
                    var new_open = node.open.copy();
                    new_open[self] = true;
    
                    new_nodes.push({
                        pressure: node.pressure + valve.flow_rate * minutes_left, 
                        open: new_open, 
                        previous: null, 
                        current: self,
                        eprevious: node.ecurrent,
                        ecurrent: etunnel
                    });
                }

                for (tunnel in tunnels) {
                    if (tunnel == node.previous) continue;
                    new_nodes.push({pressure: node.pressure, open: node.open, previous: node.current, current: tunnel,
                        eprevious: node.ecurrent,
                        ecurrent: etunnel});
                }
            }
            
        }

        nodes = new_nodes;
        // Remove the worst half
        if (minutes_left < 22 &&(minutes_left) % 2 == 0) {
            nodes.sort((a, b) -> a.pressure - b.pressure);
            nodes = nodes.slice(Math.floor(3 * nodes.length / 4));
        }
    }

    Sys.println("Part 1: " + Lambda.fold(nodes, (n, result) -> Math.max(n.pressure, result), 0));

}
