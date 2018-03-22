package main

import (
	"container/list"
	"math/rand"
)

const (
	//clubs (♣), diamonds (♦), hearts (♥), spades (♠),
	Club  	= 1
	Diamond = 2
	Heart 	= 3
	Spade 	= 4
)

const cards_per_dec =  52

type Card struct {
	suit	int
	value   int
}

type Cards struct {
	card_pool []Card
	card_list *list.List
	top_card  *list.Element
	/**
		The standard 52-card pack is used, but in most casinos several decks of card_list are shuffled together. The six-deck
		game (312 card_list) is the most popular.
	 */
	card_num int
}

func (cards *Cards) Init(deck_numbers int) {
	cards.card_num = deck_numbers * cards_per_dec
	cards.card_pool = make([]Card, deck_numbers * 52)
	cards.card_list = list.New()
	index := 0
	for i := 1; i < 5; i++ {
		for j := 1; j < 14; j++ {
			for k := 0; k < deck_numbers; k++ {
				card := Card{i, j}
				cards.card_pool[index] = card
				cards.card_list.PushBack(card)
				index++
			}
		}
	}
	cards.top_card = cards.card_list.Front()
}

func (cards *Cards) GetCard() *Card {
	if cards.top_card == nil {
		return nil
	}
	value := cards.top_card.Value
	cards.top_card = cards.top_card.Next()
	p_card := value.(Card)
	return &p_card
}

func (cards *Cards) Reshuffle() {
	candidates := list.New()
	for i := 0; i < cards.card_num; i++ {
		candidates.PushBack(i)
	}
	cards.card_list = list.New()
	length := cards.card_num
	candidates_len := length
	for i := 0; i < length; i++ {
		index := rand.Intn(candidates_len)
		node := candidates.Front()
		for j := 1; j <= index; j++ {
			node = node.Next()
		}
		cards.card_list.PushBack(cards.card_pool[node.Value.(int)])
		candidates.Remove(node)
		candidates_len--
	}
	cards.top_card = cards.card_list.Front()
}