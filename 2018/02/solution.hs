import Data.List
import Data.Maybe

counts = map length . group . sort

part1 ids = twos * threes
    where twos = length $ filter (2 `elem`) $ map counts ids
          threes = length $ filter (3 `elem`) $ map counts ids

commonLetters id1 id2 = map fst $ filter (uncurry (==)) $ zip id1 id2

correctBox id ids = if null matching then Nothing else Just $ commonLetters id $ head matching
    where matching = filter (\id' -> length (filter (==False) $ zipWith (==) id id') == 1) ids

part2 ids = head $ catMaybes $ map (\id -> correctBox id ids) ids

main = do
    ids <- lines <$> readFile "input.txt"
    print $ part1 ids
    print $ part2 ids
