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
    hasWon: false,
}));

/**
 * @type number[][]
 */
let losingBoard;
let winCount = 0;

const losingNumberIndex = numbers.findIndex(number =>
    boards.some((board, bi) =>
        board.some((row, ri) =>
            row.some((cell, ci) => {
                if (cell === number) {
                    markedNumbers[bi].rows[ri]++;
                    markedNumbers[bi].columns[ci]++;
                    if (
                        (markedNumbers[bi].rows[ri] === 5 || markedNumbers[bi].columns[ci] === 5) &&
                        !markedNumbers[bi].hasWon
                    ) {
                        if (winCount !== boards.length - 1) {
                            winCount++;
                            markedNumbers[bi].hasWon = true;
                        } else {
                            losingBoard = board;
                            return true;
                        }
                    }
                }
            })
        )
    )
);

const unmarkedNumbers = numbers.slice(losingNumberIndex + 1);

const unmarkedSum = losingBoard
    .flat()
    .filter(n => unmarkedNumbers.includes(n))
    .reduce((a, b) => a + b);

console.log(unmarkedSum * numbers[losingNumberIndex]);
