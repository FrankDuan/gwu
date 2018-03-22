package main

import (
	"container/list"
	"golang.org/x/net/dns/dnsmessage"
)

type Player struct {
	id   		int
	cards    	list.List
	stake		int
	card_sum	int
	status		int
	question    string
	answer		string
}

func (player *Player) Init() {

}

func (player *Player) AddCard(card *Card) {

}
