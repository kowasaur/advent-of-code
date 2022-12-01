use std::env;
use std::fs;
use std::cmp;

fn sum_calories(elf: &str) -> i32 {
    elf.split("\n").fold(0, |acc, cur| acc + cur.parse::<i32>().unwrap())
}

fn part1(elves: std::str::Split<&str>) {
    let most_calories = elves.fold(0, |old, elf| cmp::max(old, sum_calories(elf)));
    println!("{}", most_calories);
}

fn part2(elves: std::str::Split<&str>) {
    let mut calories: Vec<i32> = elves.map(|e| sum_calories(e)).collect();
    calories.sort_by(|a, b| b.cmp(a));
    println!("{}", calories[0] + calories[1] + calories[2]);
}

fn main() {
    let args: Vec<String> = env::args().collect();
    let path = if args.len() == 3 { "./example.txt" } else { "./input.txt" };
    let file = fs::read_to_string(path).expect("Unable to read file");

    let elves = file.split("\n\n");
    if &args[1] == "1" {
        part1(elves);
    } else {
        part2(elves);
    }
}
