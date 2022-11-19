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

	var adds [7]int
	var addToAddsTomorrow int
	var addToAddsAfterTomorrow int
	
	fish := strings.Split(string(data), ",")
	for _, s := range fish {
		day, _ := strconv.Atoi(s)
		adds[day]++
	}

	total := len(fish)

	for i := 0; i < 256; i++ {
		day := i % 7
		new := adds[day]
		total += new
		adds[day] += addToAddsTomorrow
		addToAddsTomorrow = addToAddsAfterTomorrow
		addToAddsAfterTomorrow = new
	}

	fmt.Println(total)
}
