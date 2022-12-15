const fs = require("fs");

const FILE_PATH = "input.txt";
const PART1Y = FILE_PATH == "input.txt" ? 2000000 : 10;
const MAX = FILE_PATH == "input.txt" ? 4000000 : 20;

function parseLine(l) {
    return l
        .split(": ")
        .map(part => part.split("at ")[1])
        .map(coord => coord.split(", ").map(value => parseInt(value.split("=")[1])));
}

const distance = (sx, sy, bx, by) => Math.abs(sx - bx) + Math.abs(sy - by);

const file = fs.readFileSync(FILE_PATH, "utf8").split("\n").map(parseLine);

const no_beacons = new Set();
const know_beacons = new Set();

for (const line of file) {
    const [[sx, sy], [bx, by]] = line;
    const width = distance(sx, sy, bx, by) - Math.abs(PART1Y - sy);
    for (let i = sx - width; i <= sx + width; i++) no_beacons.add(i);
    if (by === PART1Y && sx - width <= bx && bx <= sx + width) know_beacons.add(bx);
}

console.log("Part 1:", no_beacons.size - know_beacons.size);

for (let y = 0; y < MAX; y++) {
    const ranges = [];
    for (const line of file) {
        const [[sx, sy], [bx, by]] = line;
        const width = distance(sx, sy, bx, by) - Math.abs(y - sy);
        if (width >= 0) {
            const l = Math.max(sx - width, 0);
            const r = Math.min(sx + width, MAX);
            ranges.push([l, r]);
        }
    }
    // Simplify down to 1 range
    ranges.sort(([l1], [l2]) => l1 - l2);
    while (ranges.length > 2) {
        for (let i = 1; i < ranges.length; i++) {
            const [l1, r1] = ranges[i - 1];
            const [l2, r2] = ranges[i];
            if (l2 <= r1) {
                ranges.splice(i, 1);
                ranges[i - 1] = [l1, Math.max(r1, r2)];
                break;
            }
        }
    }

    const l = ranges[0][1];
    const r = ranges[1][0];
    if (r - l === 2) {
        console.log("Part 2:", ((l + r) / 2) * 4000000 + y);
        break;
    }
}
