-- This is a visualsation of the state of the octopuses

import Graphics.Vty
import Control.Concurrent
import Data.Char(digitToInt)
import System.Environment
import System.Exit (exitFailure)
import Control.Monad

replaceNth n newVal (x:xs)
   | n == 0 = newVal:xs
   | otherwise = x:replaceNth (n-1) newVal xs

replaceNth2d y x newVal list = replaceNth y (replaceNth x newVal (list!!y)) list

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

digitToImage d = string colour d
    where colour = if d == "0" then defAttr `withForeColor` yellow else defAttr

rowToImage row = horizCat $ map digitToImage row

frameToImage frame = vertCat (map rowToImage frame)

renderFrame n frames vty s m = do
    let image = frameToImage $ frames!!n
    let pic = picForImage $ image <-> string (defAttr `withForeColor` green) ("Step " ++ show n)
    update vty pic

    threadDelay $ s * 1000
    when (n + 1 /= m) $ renderFrame (n + 1) frames vty s m

main = do
    args <- getArgs
    contents <- readFile "./example_input.txt"
    let octopuses = parseInput contents
    let frames = map (map (map show)) $ infiniteSteps octopuses

    when (length args /= 2) $ do
        print "You must supply delay in milliseconds and max run time in seconds"
        exitFailure

    let speed = read $ head args
    let maxStep = 1000 * read (args!!1) `div` speed

    cfg <- standardIOConfig
    vty <- mkVty cfg

    renderFrame 0 frames vty speed maxStep

    shutdown vty
