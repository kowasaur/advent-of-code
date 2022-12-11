import fs from "fs";

interface Monkey {
    items: number[];
    operation: string;
    test_divisibility: number;
    test_true: number;
    test_false: number;
    inspections: number;
}

function parseMonkey(chunk: string): Monkey {
    const line = chunk.split("\n");
    return {
        items: eval(`[${line[1].split(": ")[1]}]`),
        operation: line[2].split("= ")[1],
        test_divisibility: Number(line[3].split("by ")[1]),
        test_true: Number(line[4].split("monkey ")[1]),
        test_false: Number(line[5].split("monkey ")[1]),
        inspections: 0,
    };
}

const monkeys = fs.readFileSync("input.txt", "ascii").split("\n\n").map(parseMonkey);

function monkeyTurn(monkey: Monkey) {
    const { items, operation, test_divisibility, test_false, test_true } = monkey;
    let old: number | undefined;
    while ((old = items.shift())) {
        monkey.inspections++;
        const worry_level = Math.floor(eval(operation) / 3);
        const throw_to = worry_level % test_divisibility == 0 ? test_true : test_false;
        monkeys[throw_to].items.push(worry_level);
    }
}

for (let i = 0; i < 20; i++) {
    for (const monkey of monkeys) monkeyTurn(monkey);
}

const inspections = monkeys.map(m => m.inspections).sort((a, b) => b - a);
const monkey_business = inspections[0] * inspections[1];
console.log(monkey_business);
