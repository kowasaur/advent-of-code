import fs from "fs";

type Op = (a: number, b: number) => number;

interface Operation {
    changeItem: Op;
    by: number | "old";
}

interface Monkey {
    items: number[];
    operation: Operation;
    divisor: number;
    test_true: number;
    test_false: number;
    inspections: number;
}

function parseOperation(operation: string): Operation {
    const equation = operation.split(" ");
    return {
        changeItem: equation[1] == "*" ? (a, b) => a * b : (a, b) => a + b,
        by: equation[2] == "old" ? "old" : Number(equation[2]),
    };
}

function parseMonkey(chunk: string): Monkey {
    const line = chunk.split("\n");
    return {
        items: eval(`[${line[1].split(": ")[1]}]`),
        operation: parseOperation(line[2].split("= ")[1]),
        divisor: Number(line[3].split("by ")[1]),
        test_true: Number(line[4].split("monkey ")[1]),
        test_false: Number(line[5].split("monkey ")[1]),
        inspections: 0,
    };
}

function monkeyBusiness(part: number) {
    const monkeys = fs.readFileSync("input.txt", "ascii").split("\n\n").map(parseMonkey);
    const rounds = part === 1 ? 20 : 10000;

    // A comment on reddit told me to use lcm as the base
    // It's just the product since they are all prime
    const lcm = monkeys.reduce((product, monkey) => product * monkey.divisor, 1);

    const worryLevel =
        part === 1
            ? (a: number, b: number, changeItem: Op) => Math.floor(changeItem(a, b) / 3)
            : (a: number, b: number, changeItem: Op) => changeItem(a, b) % lcm;

    function monkeyTurn(monkey_index: number) {
        const monkey = monkeys[monkey_index];
        const { items, operation, test_false, test_true, divisor } = monkey;
        let item: number | undefined;
        monkey.inspections += items.length;

        while ((item = items.shift())) {
            const by = operation.by === "old" ? item : operation.by;
            const worry_level = worryLevel(item, by, operation.changeItem);

            const throw_to = worry_level % divisor === 0 ? test_true : test_false;
            monkeys[throw_to].items.push(worry_level);
        }
    }

    for (let r = 0; r < rounds; r++) {
        for (let i = 0; i < monkeys.length; i++) monkeyTurn(i);
    }

    const inspections = monkeys.map(m => m.inspections).sort((a, b) => b - a);
    const monkey_business = inspections[0] * inspections[1];
    console.log(`Part ${part}: ${monkey_business}`);
}

monkeyBusiness(1);
monkeyBusiness(2);
