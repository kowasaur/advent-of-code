use std::fs;
use std::collections::HashMap;

fn pair_insert(polymer: String, rules: &HashMap<&str, &str>) -> String {
    let mut new_polymer = "".to_owned();
    for window in polymer.as_bytes().windows(2) {
        let key = String::from_utf8_lossy(window);
        new_polymer.push(key.chars().nth(0).unwrap());
        new_polymer.push_str(rules.get(&key as &str).unwrap());
    }
    new_polymer.push(polymer.chars().last().unwrap());
    new_polymer
}

fn main() {
    let file = fs::read_to_string("./input.txt").expect("Unable to read file");
    let parts = file.split("\n\n").collect::<Vec<&str>>();

    let polymer = parts[0].to_string();
    let mut insertion_rules = HashMap::new();
    
    for rule in parts[1].lines() {
        let elements = rule.split(" -> ").collect::<Vec<&str>>();
        insertion_rules.insert(elements[0], elements[1]);
    }
    
    let polymer_10 = (0..10).fold(polymer, |acc, _| pair_insert(acc, &insertion_rules));

    let mut max = polymer_10.matches("B").count();
    let mut min = polymer_10.matches("B").count();
    for c in insertion_rules.values() {
        let count = polymer_10.matches(c).count();
        if count > max { 
            max = count;
        }
        else if count < min {
            min = count;
        }
    }

    println!("{}", max - min);
}
