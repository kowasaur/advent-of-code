package main

import (
	"fmt"
	"os"
	"strings"
	"strconv"
)


func main() {
	data, err := os.ReadFile("./input.txt")
	if err != nil { panic(err) }

	stringFish := strings.Split(string(data), ",")
	// Convert string[] to int[]
	fish := make([]int, len(stringFish))
	for i, s := range stringFish {
		fish[i], _ = strconv.Atoi(s)
	}

	for x := 0; x < 80; x++ {
		for index, element := range fish {
			if element == 0 {
				fish[index] = 6
				fish = append(fish, 8)
			} else {
				fish[index] = element - 1
			}
		}
	}

	fmt.Print(len(fish))
}
