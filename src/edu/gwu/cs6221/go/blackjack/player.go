package main

import (
	"log"
	//"os"
)

type Player struct {
	id   		int
	name        string
	cards    	[]*Card
	stake		int
	card_sum	int
	status		int
	question    string
	answer		string
}

func (player *Player) Init(name string) {
	/*
	f, err := os.OpenFile("logFile", os.O_RDWR | os.O_CREATE | os.O_APPEND, 0666)
	if err != nil {
		return
	}
	defer f.Close()

	log.SetOutput(f) */
	player.cards = make([]*Card, 0)
	player.card_sum = 0
	player.name = name
}

func (player *Player) AddCard(card *Card) {
	player.cards = append(player.cards, card)
	sum := 0
	for i := 0; i < len(player.cards); i++ {
		if player.cards[i].Value >= 10 {
			sum += 10
		} else {
			sum += player.cards[i].Value
		}
	}
	for i := 0; i < len(player.cards); i++ {
		if player.cards[i].Value == 1 && sum <= 11 {
			sum += 10
		}
	}
	player.card_sum = sum
	log.Println(player.GetCards(false))
	log.Println(player.name, "get card ", card.Value, " total ", player.card_sum)
}

func (player *Player) GetCards(hide_first bool) []Card {
	cards := make([]Card, len(player.cards))
	for i := 0; i < len(player.cards); i++ {
		cards[i].Value = player.cards[i].Value
		cards[i].Suit = player.cards[i].Suit
	}

	if hide_first {
		cards[0].Value = 0
		cards[0].Suit = 0
	}

	return cards
}