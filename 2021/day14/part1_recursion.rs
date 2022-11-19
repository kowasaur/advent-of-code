// This was my attempt to do part 2 but it is still too inefficient to work

use std::fs;
use std::collections::HashMap;

fn increment_map(map: &mut HashMap<char, u64>, key: char) {
    if map.get(&key).is_some() {
        map.insert(key, map.get(&key).unwrap() + 1);
    } else {
        map.insert(key, 1);
    }
}

fn letter_count(polymer: String, rules: &HashMap<&str, &str>, counts: &mut HashMap<char, u64>, max: i32) {
    if max != 0 {
        let new_char = rules.get(&polymer as &str).unwrap();
        increment_map(counts,  new_char.chars().next().unwrap());

        let mut polymer1 = String::new();
        polymer1.push(polymer.chars().next().unwrap());
        polymer1.push_str(new_char);
        let mut polymer2 = String::new();
        polymer2.push_str(new_char);
        polymer2.push(polymer.chars().last().unwrap());

        letter_count(polymer1, rules, counts, max - 1);
        letter_count(polymer2, rules, counts, max - 1);
    }
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
    
    let mut counts: HashMap<char, u64> = HashMap::new();
    
    for c in polymer.chars() {
        increment_map(&mut counts,  c);
    }

    for window in polymer.as_bytes().windows(2) {
        let key = String::from_utf8_lossy(window);
        letter_count(key.to_string(), &insertion_rules, &mut counts, 10);
    }

    let mut max = counts[&'B'];
    let mut min = counts[&'B'];
    for c in insertion_rules.values() {
        let count = counts[&c.chars().next().unwrap()];
        if count > max { 
            max = count;
        }
        else if count < min {
            min = count;
        }
    }

    println!("{:?}", max - min);
}
