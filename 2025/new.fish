#!/bin/fish
set d $argv[1]
if test (string length $d) -eq 1
    set d "0$d"
end

mkdir $d
cp template.py $d/solution.py
touch $d/sample.txt

source env.fish
wget --no-cookies --header "Cookie: session=$AOC_SESSION" -O $d/input.txt https://adventofcode.com/2025/day/$argv[1]/input

cd $d
nvim solution.py -c ":vne" -c ":terminal"
