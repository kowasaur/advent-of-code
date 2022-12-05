import os
import strutils
import sequtils
import algorithm
import sugar

type State = seq[seq[char]]
type Instruction = tuple[amount: int, fro: int, to: int]

proc parse_instruction(instruction: string): Instruction =
    let parts = instruction.split(" ")
    return (amount: parseInt(parts[1]), fro: parseInt(parts[3])-1, to: parseInt(parts[5])-1)

proc parse_init_state(init_state: string): State =
    var lines = init_state.splitLines()
    discard lines.pop()
    lines.reverse()
    let labels_len = lines[0].len() - 1
    var state: State = newSeq[seq[char]]()

    var i = 1
    while i < labels_len:
        var stack: seq[char] = newSeq[char]()
        for line in lines:
            let crate = line[i]
            if crate != ' ':
                stack.add(crate)
        state.add(stack)
        i += 4

    return state

proc part1(instruction: Instruction, state: var State) =
    for _ in 1..instruction.amount:
        let crate = state[instruction.fro].pop()
        state[instruction.to].add(crate)

proc part2(instruction: Instruction, state: var State) =
    let from_stack = state[instruction.fro]
    let end_ind = from_stack.len()-1
    let crates = from_stack[end_ind-instruction.amount+1..end_ind]
    state[instruction.to] = state[instruction.to].concat(crates)
    for _ in 1..instruction.amount:
        discard state[instruction.fro].pop()

let file_path = if paramCount() == 2: "example.txt" else: "input.txt"
let inital_state_and_instructions = readFile(file_path).split("\n\n")
let instructions = inital_state_and_instructions[1].splitLines().map(parse_instruction)
var state = parse_init_state(inital_state_and_instructions[0])

let changeState = if paramStr(1) == "1": part1 else: part2

for instruction in instructions:
    changeState(instruction, state)

echo state.map(stack => stack[stack.len() - 1]).join()
