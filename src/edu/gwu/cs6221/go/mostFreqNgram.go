package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"regexp"
	"strconv"
	"strings"
)

func main() {
	args := os.Args
	inFile := args[1]
	ngramLen, error := strconv.Atoi(args[2])
	if error != nil {
		log.Fatal(error)
		os.Exit(2)
	}
	if ngramLen < 2 || ngramLen > 5 {
		log.Fatal("Invalid ngram_len, should range from 2 to 5")
		os.Exit(2)
	}

	fd, error := os.Open(inFile)
	if error != nil {
		log.Fatal(error)
		os.Exit(2)
	}
	defer fd.Close()

	scanner := bufio.NewScanner(fd)
	ngrams := make(map[string]int)

	reg, error := regexp.Compile("[^a-z0-9]+")
	if error != nil {
		log.Fatal(error)
		os.Exit(2)
	}

	for scanner.Scan() {
		text := strings.ToLower(scanner.Text())
		text = reg.ReplaceAllString(text, " ")
		words := strings.Fields(text)
		for _, word := range words {
			end := len(word) - ngramLen
			for i := 0; i <= end; i++ {
				ngram := word[i : i+ngramLen]
				_, present := ngrams[ngram]
				if present {
					ngrams[ngram]++
				} else {
					ngrams[ngram] = 1
				}
			}
		}
	}

	if error := scanner.Err(); error != nil {
		log.Fatal(error)
		os.Exit(2)
	}

	max := 0
	for _, value := range ngrams {
		if max < value {
			max = value
		}
	}

	for key, value := range ngrams {
		if max == value {
			fmt.Printf("N-gram: %s, frequency: %d\n", key, value)
		}
	}
}
