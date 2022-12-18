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

func invalidCoord(coord [3]int) bool {
    for _, part := range coord {
        if part < 0 || part >= 24 { return true }
    }
    return false
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

    exterior_surface_area := 0
    var lava [24][24][24]bool
    checked := [24][24][24]bool{{{true}}}
    will_check := [][3]int{{0, 0, 0}}    

    for _, coord := range coords {
        // Add padding
        lava[coord[2]+1][coord[1]+1][coord[0]+1] = true
    }

    for len(will_check) > 0 {
        new_will_check := [][3]int{}
        for _, coord := range will_check {
            x := coord[0]
            y := coord[1]
            z := coord[2]

            for _, i := range [...]int{-1, 1} {
                for _, change := range [...][3]int{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}} {
                    new_coord := [...]int{x + i * change[0], y + i * change[1], z + i * change[2]}
                    if invalidCoord(new_coord) || checked[new_coord[2]][new_coord[1]][new_coord[0]] { continue }

                    if lava[new_coord[2]][new_coord[1]][new_coord[0]] {
                        exterior_surface_area++
                        continue
                    }

                    checked[new_coord[2]][new_coord[1]][new_coord[0]] = true
                    new_will_check = append(new_will_check, new_coord)
                }
            }
        }
        will_check = new_will_check
    }

    fmt.Printf("Part 2: %d\n", exterior_surface_area)

}
