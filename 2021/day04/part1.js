const fs = require("fs");

const [stringNumbers, ...stringBoards] = fs.readFileSync("input.txt", "utf8").split("\n\n");

const numbers = stringNumbers.split(",").map(n => Number(n));

const boards = stringBoards.map(board =>
    board
        .split("\n")
        .map(line => line.split(/\s+/).flatMap(cell => (cell === "" ? [] : [Number(cell)])))
);

// How many numbers have been marked in each board for each row and column
const markedNumbers = boards.map(() => ({
    rows: new Array(5).fill(0),
    columns: new Array(5).fill(0),
}));

/**
 * @type number[][]
 */
let winningBoard;

const winningNumberIndex = numbers.findIndex(number =>
    boards.some((board, bi) =>
        board.some((row, ri) =>
            row.some((cell, ci) => {
                if (cell === number) {
                    markedNumbers[bi].rows[ri]++;
                    markedNumbers[bi].columns[ci]++;
                    if (markedNumbers[bi].rows[ri] === 5 || markedNumbers[bi].columns[ci] === 5) {
                        winningBoard = board;
                        return true;
                    }
                }
            })
        )
    )
);

const unmarkedNumbers = numbers.slice(winningNumberIndex + 1);

const unmarkedSum = winningBoard
    .flat()
    .filter(n => unmarkedNumbers.includes(n))
    .reduce((a, b) => a + b);

console.log(unmarkedSum * numbers[winningNumberIndex]);
