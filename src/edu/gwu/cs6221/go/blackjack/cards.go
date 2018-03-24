package main

import (
	"container/list"
	"math/rand"
	"time"
)

const (
	//clubs (♣), diamonds (♦), hearts (♥), spades (♠),
	Club  	= 1
	Diamond = 2
	Heart 	= 3
	Spade 	= 4
)

var suitMap = map[int]string {
	Club: "Club",
	Diamond: "Diamond",
	Heart: "Heart",
	Spade: "Spade",
}

const cards_per_dec =  52

type Card struct {
	Suit  int `json:"Suit"`
	Value int `json:"Value"`
}

type Cards struct {
	card_pool []*Card
	card_list []*Card
	cur_index int
	/**
		The standard 52-card pack is used, but in most casinos several decks of card_list are shuffled together. The six-deck
		game (312 card_list) is the most popular.
	 */
	card_num int

}

func (cards *Cards) Init(deck_numbers int) {
	cards.card_num = deck_numbers * cards_per_dec
	cards.card_pool = make([]*Card, deck_numbers * 52)
	cards.card_list = make([]*Card, deck_numbers * 52)
	index := 0
	for i := 1; i < 5; i++ {
		for j := 1; j < 14; j++ {
			for k := 0; k < deck_numbers; k++ {
				card := Card{i, j}
				cards.card_pool[index] = &card
				cards.card_list[index] = &card
				index++
			}
		}
	}
	cards.cur_index = 0
}

func (cards *Cards) GetCard() *Card {
	if cards.cur_index >=  cards.card_num {
		cards.Reshuffle()
	}
	card := cards.card_list[cards.cur_index]
	cards.cur_index++
	return card
}

func (cards *Cards) Reshuffle() {
	candidates := list.New()
	for i := 0; i < cards.card_num; i++ {
		candidates.PushBack(i)
	}
	length := cards.card_num
	candidatesLen := length
	for i := 0; i < length; i++ {
		rand.Seed(time.Now().Unix())
		index := rand.Intn(candidatesLen)
		node := candidates.Front()
		for j := 1; j <= index; j++ {
			node = node.Next()
		}
		cards.card_list[i] = cards.card_pool[node.Value.(int)]
		candidates.Remove(node)
		candidatesLen--
	}
	cards.cur_index = 0
}