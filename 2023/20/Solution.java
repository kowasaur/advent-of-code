import java.io.IOException;
import java.util.*;
import utils.*;

class Solution {
    static int button_presses = 0;

    static void pressButton(Map<String, Module> modules) {
        var to_send = new ArrayList<Pulses>();
        to_send.add(new Pulses(new String[] {"broadcaster"}, false, "button"));
        while (to_send.size() > 0) {
            var new_send = new ArrayList<Pulses>();
            for (var p : to_send) {
                new_send.addAll(p.send(modules));
            }
            to_send = new_send;
        }
    }

    public static void main(String[] _args) throws IOException {
        var lines = AoC.readLines("20/input.txt");
        var modules = new HashMap<String, Module>();
        for (String line : lines) {
            var parts = line.split(" -> ");
            var key = parts[0];
            var key_fr = key.substring(1);
            if (key.charAt(0) == '%') modules.put(key_fr, new FlipFlop(key_fr, parts[1]));
            else if (key.charAt(0) == '&') modules.put(key_fr, new Conjunction(key_fr, parts[1]));
            else modules.put(key, new Broadcast(key, parts[1]));
        }

        // inital conjunction setup
        for (var entry : modules.entrySet()) {
            for (String dest : entry.getValue().destinations) {
                var mod = modules.get(dest);
                if (mod != null) mod.initMemory(entry.getKey());
            }
        }

        for (; button_presses < 1000; button_presses++) pressButton(modules);
        AoC.printResult(1, Pulses.low * Pulses.high);
        for(;;button_presses++) pressButton(modules);
    }
}

abstract class Module {
    String name;
    String[] destinations;

    public Module(String name, String dests) {
        this.name = name;
        destinations = dests.split(", ");
    }

    public abstract Pulses receivePulse(boolean pulse, String input);
    public void initMemory(String input) {}
}

class FlipFlop extends Module {
    boolean on;

    public FlipFlop(String name, String dests) {
        super(name, dests);
    }

    @Override
    public Pulses receivePulse(boolean pulse, String __) {
        if (pulse) return null;
        on = !on;
        return new Pulses(destinations, on, name);
    }
    
}

class Conjunction extends Module {
    HashMap<String, Boolean> memory = new HashMap<String, Boolean>();

    public Conjunction(String name, String dests) {
        super(name, dests);
    }

    @Override
    public Pulses receivePulse(boolean pulse, String input) {
        memory.put(input, pulse);
        for (var p : memory.values()) {
            if (!p) return new Pulses(destinations, true, name);
        }
        return new Pulses(destinations, false, name);
    }

    @Override
    public void initMemory(String input) {
        memory.put(input, false);
    }
}

class Broadcast extends Module {
    public Broadcast(String name, String dests) {
        super(name, dests);
    }

    @Override
    public Pulses receivePulse(boolean pulse, String __) {
        return new Pulses(destinations, pulse, name);
    }
}

class Pulses {
    String[] destinations;
    boolean pulse;
    String from;

    static long low;
    static long high;

    public Pulses(String[] destinations, boolean pulse, String from) {
        this.destinations = destinations;
        this.pulse = pulse;
        this.from = from;
    }

    public List<Pulses> send(Map<String, Module> modules) {
        if (pulse) high += destinations.length;
        else low += destinations.length;
        return Arrays.stream(destinations).map(d -> sendReceive(d, modules)).filter(p -> p != null).toList();
    }

    Pulses sendReceive(String dest, Map<String, Module> modules) {
        if (dest.equals("rx") && !pulse) {
            AoC.printResult(2, Solution.button_presses+1);
            System.exit(0);
        }
        var mod = modules.get(dest);
        if (mod == null) return null;
        return mod.receivePulse(pulse, from);
    }
}
