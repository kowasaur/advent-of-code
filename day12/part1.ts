import fs from "fs";

type Caves = {
    [label: string]: string[]; // array of labels
};

const caves: Caves = {};

const file = fs.readFileSync("./input.txt", "utf8").split("\n");
file.forEach(line => {
    const [a, b] = line.split("-");
    if (b !== "start" && a !== "end") {
        if (caves[a]) caves[a].push(b);
        else caves[a] = [b];
    }
    if (a !== "start" && b !== "end") {
        if (caves[b]) caves[b].push(a);
        else caves[b] = [a];
    }
});

function isUpper(str: string) {
    return str.toUpperCase() === str;
}

function getPaths(paths: string[][]): string[][] {
    const newPaths: string[][] = [];
    paths.forEach(path => {
        const last = path.at(-1)!;
        if (last === "end") {
            newPaths.push(path);
        } else {
            caves[last].forEach(cave => {
                if (isUpper(cave) || !path.includes(cave)) newPaths.push([...path, cave]);
            });
        }
    });
    if (newPaths.every(path => path.at(-1) === "end")) return newPaths;
    return getPaths(newPaths);
}

const paths = getPaths([["start"]]);

console.log(paths.length);
