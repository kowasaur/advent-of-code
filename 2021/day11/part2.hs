import Data.Char(digitToInt)

replaceNth n newVal (x:xs)
   | n == 0 = newVal:xs
   | otherwise = x:replaceNth (n-1) newVal xs

replaceNth2d y x newVal list = replaceNth y (replaceNth x newVal (list!!y)) list

allTheSame list = all (== head list) (tail list)

parseInput input = map (map digitToInt) $ words input

increase = map (map (+1))

increaseFromFlash octos y x cy cx = if inList && octos!!ny!!nx /= 0
    then flash (replaceNth2d ny nx (octos!!ny!!nx + 1) octos) ny nx
    else octos
    where inList = nx >= 0 && nx < length (head octos) && ny >= 0 && ny < length octos
          nx = x + cx
          ny = y + cy

flash octos y x = if octos!!y!!x > 9
    -- then increaseFromFlash resetted 0 0 y x
    then foldl (\acc1 e1 -> foldl (\acc2 e2 -> increaseFromFlash acc2 y x e2 e1) acc1 [-1,0,1]) resetted [-1,0,1]
    else octos
    where resetted = replaceNth2d y x 0 octos

flashAll octos = foldl (\acc1 e1 -> foldl (\acc2 e2 -> flash acc2 e2 e1) acc1 indicies) octos indicies
    where indicies = [0..length octos - 1]

step octos = flashAll $ increase octos

infiniteSteps = iterate step

firstSynchronised steps n = if allTheSame . concat $ steps!!n
    then n
    else firstSynchronised steps (n + 1)

main = do
    contents <- readFile "./input.txt"
    let octopuses = parseInput contents
    let synchronisedStep = firstSynchronised (infiniteSteps octopuses) 0
    print synchronisedStep
