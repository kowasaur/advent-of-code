import qualified Data.Set as Set

readDiff str 
    | head str == '+' = read $ tail str
    | otherwise = read str

part1 = sum

cumSum = tail . scanl (+) 0

twice (d:ds) set
    | Set.member d set = d
    | otherwise = twice ds (Set.insert d set)

part2 diffs = twice (cumSum . cycle $ diffs) Set.empty

main = do
    contents <- readFile "input.txt"
    let diffs = map readDiff $ lines contents
    print $ part1 diffs
    print $ part2 diffs

