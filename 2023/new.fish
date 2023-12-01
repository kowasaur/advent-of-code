#!/bin/fish
set d $argv[1]
if test (string length $d) -eq 1
    set d "0$d"
end

mkdir $d
cp template.java $d/Solution.java
touch $d/sample.txt

source env.fish
wget --no-cookies --header "Cookie: session=$AOC_SESSION" -O $d/input.txt https://adventofcode.com/2023/day/$argv[1]/input
