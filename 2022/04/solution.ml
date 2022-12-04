(* found here: https://stackoverflow.com/a/53840784/14746108 *)
let read_whole_file filename =
    let ch = open_in filename in
    let s = really_input_string ch (in_channel_length ch) in
    close_in ch;
    s
;;

let split by str = Str.split (Str.regexp by) str;;

let contains_range x y overlap_func  =
    let xlow = int_of_string (List.nth x 0) in
    let xupp = int_of_string (List.nth x 1) in
    let ylow = int_of_string (List.nth y 0) in
    let yupp = int_of_string (List.nth y 1) in
    overlap_func xlow xupp ylow yupp
;;

let does_overlap line overlap_func =
    let pair = split "," line in
    let range1 = split "-" (List.nth pair 0) in
    let range2 = split "-" (List.nth pair 1) in
    (contains_range range1 range2 overlap_func) || (contains_range range2 range1 overlap_func)
;;

let num_of_overlaps overlap_func lines = 
    List.fold_left (fun acc x -> acc + if does_overlap x overlap_func then 1 else 0) 0 lines;;

let fully_contains xlow xupp ylow yupp = ylow >= xlow && yupp <= xupp;;
let overlaps_at_all xlow xupp ylow yupp = ylow >= xlow && ylow <= xupp;;

let lines = split "\n" (read_whole_file "input.txt") in
let part1_result = num_of_overlaps fully_contains lines in
print_string "Part 1: ";
print_int part1_result;
print_string "\nPart 2: ";
let part2_result = num_of_overlaps overlaps_at_all lines in
print_int part2_result;
print_string "\n";
