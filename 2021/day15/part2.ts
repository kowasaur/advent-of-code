import { readFileSync } from "fs";

// prettier-ignore
const neighbourDirections = [[1,0],[0,1],[-1,0],[0,-1]];

class Node {
    public gScore: number = Infinity;
    constructor(readonly x: number, readonly y: number, readonly risk: number) {}

    public getNeigbours() {
        const neighbours: Node[] = [];
        neighbourDirections.forEach(([y, x]) => {
            const nx = this.x + x;
            const ny = this.y + y;

            if (nx >= 0 && nx < nodes[0].length && ny >= 0 && ny < nodes.length) {
                neighbours.push(nodes[ny][nx]);
            }
        });

        return neighbours;
    }
}

const invalidNode = new Node(-1, -1, -1); // used for initial comparison

function minNode(nodeSet: Set<Node>) {
    let min = invalidNode;
    nodeSet.forEach(n => {
        if (n.gScore < min.gScore) min = n;
    });
    return min;
}

function wrapNum(n: number) {
    return (n % 10) + Number(n > 9);
}

const initialNodes = readFileSync("./input.txt", "utf8")
    .split("\n")
    .map(l => l.split(""));

const nodes: Node[][] = Array.from(Array(initialNodes.length * 5), () => []);
for (let y = 0; y < 5; y++) {
    for (let x = 0; x < 5; x++) {
        initialNodes.forEach((row, yi) => {
            const newY = yi + initialNodes.length * y;
            nodes[newY].push(
                ...row.map((e, xi) => {
                    const risk = wrapNum(Number(e) + x + y);
                    return new Node(xi + initialNodes.length * x, newY, risk);
                })
            );
        });
    }
}

const open = new Set<Node>();

const start = nodes[0][0];
start.gScore = 0;
const target = nodes.at(-1)?.at(-1)!;

open.add(start);

while (open.size > 0) {
    const currentNode = minNode(open);
    if (currentNode === target) break;

    open.delete(currentNode);

    currentNode.getNeigbours().forEach(neighbour => {
        const gScoreNew = currentNode.gScore + neighbour.risk;
        if (gScoreNew < neighbour.gScore) {
            neighbour.gScore = gScoreNew;
            open.add(neighbour);
        }
    });
}

console.log(target.gScore);
