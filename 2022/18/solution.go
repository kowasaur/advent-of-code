package main

import (
    "fmt"
    "os"
    "strings"
    "strconv"
)

func oneDifferent(x, y int) bool {
    if x < y { return y - x == 1 }
    return x - y == 1
}

func shareSide(p1, p2 [3]int) bool {
    same := 0
    var unique1, unique2 int
    for i := 0; i < 3; i++ {
        if p1[i] == p2[i] { 
            same++ 
        } else {
            unique1 = p1[i]
            unique2 = p2[i]
        }
    }
    return same == 2 && oneDifferent(unique1, unique2)
}

func amountAdjacent(coordington [][][]bool, x, y, z int) int {
    amount := 0
    for _, i := range [...]int{-1, 1} {
        if coordington[z][y][x + i] { amount++ }
        if y + i >= 0 && len(coordington[z][y + i]) > 0 && coordington[z][y + i][x] { amount++ }
        if len(coordington[z + i][y]) > 0 && coordington[z + i][y][x] { amount++ }
    }
    return amount
}

func main() {
    data, err := os.ReadFile("./input.txt")
    if err != nil { panic(err) }
    file := strings.Split(string(data), "\n")

    coords := make([][3]int, len(file))
    for i, line := range file {
        line_split := strings.Split(line, ",")
        for j := 0; j < 3; j++ {
            coords[i][j], _ = strconv.Atoi(line_split[j])
        }
    }

    surface_area := len(coords) * 6

    for i := 0; i < len(coords); i++ {
        for j := 0; j < len(coords); j++ {
            if i != j && shareSide(coords[i], coords[j]) { surface_area-- }
        }
    }

    fmt.Printf("Part 1: %d\n", surface_area)

    coordington := make([][][]bool, 22)
    for _, coord := range coords {
        if len(coordington[coord[2]]) == 0 { coordington[coord[2]] = make([][]bool, 22) }
        if len(coordington[coord[2]][coord[1]]) == 0 { coordington[coord[2]][coord[1]] = make([]bool, 22) }
        coordington[coord[2]][coord[1]][coord[0]] = true
    }

    min_z := 0
    for len(coordington[min_z]) == 0 { min_z++ }
    max_z := 21
    for len(coordington[max_z]) == 0 { max_z-- }

    for z := min_z + 1; z < max_z - 1; z++ {
        min_y := 0
        for len(coordington[z][min_y]) == 0 { min_y++ }
        max_y := 21
        for len(coordington[z][max_y]) == 0 { max_y-- }

        for y := min_y + 1; y < max_y - 1; y++ {
            min_x := 0
            for !coordington[z][y][min_x] { min_x++ }
            max_x := 21
            for !coordington[z][y][max_x] { max_x-- }

            for x := min_x + 1; x < max_x - 1; x++ {
                if !coordington[z][y][x] { 
                    surface_area -= amountAdjacent(coordington, x, y, z)
                }
            }
        }
    }

    fmt.Println(surface_area)
}
