/*
    Made using help from these: 
    https://www.youtube.com/watch?v=-L-WgKMFuhE
    https://en.wikipedia.org/wiki/A*_search_algorithm
    I didn't implement a heuristic function though 
    so I think it's actually Dijkstra's algorithm
*/

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

const nodes = readFileSync("./input.txt", "utf8")
    .split("\n")
    .map((l, y) => l.split("").map((n, x) => new Node(x, y, Number(n))));

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
