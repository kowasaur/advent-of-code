import fs from "fs";

type Caves = {
    [label: string]: string[]; // array of labels
};

type Path = {
    path: string[];
    smallVisited: boolean;
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

function isLower(str: string) {
    return str.toLowerCase() === str;
}

function getPaths(paths: Path[]): Path[] {
    const newPaths: Path[] = [];
    paths.forEach(path => {
        const last = path.path.at(-1)!;
        if (last === "end") {
            newPaths.push(path);
        } else {
            caves[last].forEach(cave => {
                let smallVisited = path.smallVisited;
                if (isLower(cave) && path.path.includes(cave)) {
                    if (path.smallVisited) return;
                    smallVisited = true;
                }
                newPaths.push({ path: [...path.path, cave], smallVisited });
            });
        }
    });
    if (newPaths.every(path => path.path.at(-1) === "end")) return newPaths;
    return getPaths(newPaths);
}

const paths = getPaths([{ path: ["start"], smallVisited: false }]);

console.log(paths.length);
