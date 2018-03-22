package main

import (
	"fmt"
)

func main() {
	n := 3
	x := [][]int{
		{0, 1, 2, 3},
		{4, 5, 6, 7},
		{0, 0, 0, 0},
		{2, 8, 3, 1},
	}
	i := 0
	j := 0
	for i = 1; i <= n; i++ {
		for j = 1; j <= n; j++ {
			if x[i][j] != 0 {
				break
			}
		}
		if j > n {
			fmt.Println("First all­zero row is:", i)
			break
		}
	}
}
