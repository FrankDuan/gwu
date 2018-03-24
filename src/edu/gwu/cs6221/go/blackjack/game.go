package main

import (
	"log"
	"fmt"
)

const (
	game_status_none = 0
	player_hit_hold  = 1
	dealer_hit       = 2
	player_succeed   = 3
	dealer_succeed   = 4
	none_succeed     = 5
)

var statusMap = map[int]string {
	game_status_none: "",
	player_hit_hold:  "Click hit or stand button!",
	dealer_hit:       "Dealer hit",
	player_succeed:   "Player succeed!",
	dealer_succeed:   "Dealer succeed!",
	none_succeed:     "No one succeed!",
}

type Game struct {
	cards 	*Cards
	player	*Player
	dealer  *Player
	status  int
	message string
	extra_msg string
}

type GameStatus struct {
	DealerCards     []Card 		`json:"DealerCards"`
	PlayerCards     []Card 		`json:"PlayerCards"`
	Message 		string 		`json:"Message"`
}

func (game *Game)Init() {
	game.cards = new(Cards)
	game.cards.Init(4)
	game.cards.Reshuffle()
	game.player = new(Player)
	game.dealer = new(Player)
	game.extra_msg = ""
}

func (game *Game)Start() {
	//1. give dealer and every player two cards.
	game.player.Init("Player")
	pCard := game.cards.GetCard()
	game.player.AddCard(pCard)
	pCard = game.cards.GetCard()
	game.player.AddCard(pCard)

	game.dealer.Init("Dealer")
	pCard = game.cards.GetCard()
	game.dealer.AddCard(pCard)
	pCard = game.cards.GetCard()
	game.dealer.AddCard(pCard)

	//2. check if players have blackjack, check if dealer has blackjack,
	if game.player.card_sum == 21 && game.dealer.card_sum == 21 {
		game.status = none_succeed
		game.extra_msg = "Double Blackjack! "
	} else if game.player.card_sum == 21 {
		game.status = player_succeed
		game.extra_msg = "Player Blackjack! "
	} else if game.dealer.card_sum == 21 {
		game.status = dealer_succeed
		game.extra_msg = "Dealer Blackjack! "
	} else {
		game.status = player_hit_hold
		game.extra_msg = ""
	}
}

func (game *Game)PlayerHit() {
	if game.status != player_hit_hold {
		log.Println("Unexpected status: ", game.status)
		return
	}
	pCard := game.cards.GetCard()
	game.player.AddCard(pCard)

	// if player bust or blackjact, end game. else go on
	if game.player.card_sum == 21 {
		log.Println("Player blackjack!")
		game.extra_msg = "Player Blackjack! "
		game.status = player_succeed
	} else if game.player.card_sum > 21 {
		log.Println("Player bust!")
		game.extra_msg = "Player bust! "
		game.status = dealer_succeed
	}
	// else: query player1: hit or hold?
	//keep query player
}

func (game *Game)PlayerHold() {
	if game.status != player_hit_hold {
		log.Println("Unexpected status: ", game.status)
		return
	}
	game.status = dealer_hit
	game.DealerHit()
}

func (game *Game)DealerHit() {
	//1. if dealer less bigger than 17, judge, end. else go on.
	if game.dealer.card_sum > 21 {
		game.status = player_succeed
		game.extra_msg = "Dealer bust! "
		return
	} else if game.dealer.card_sum >= 17 {
		game.extra_msg = fmt.Sprintf("Dealer %d, player %d. ",
			game.dealer.card_sum, game.player.card_sum)
		if game.dealer.card_sum > game.player.card_sum {
			game.status = dealer_succeed
		} else if game.dealer.card_sum == game.player.card_sum {
			game.status = none_succeed
		} else {
			game.status = player_succeed
		}
		return
	}

	//2. give dealer one card. go to 1
	pCard := game.cards.GetCard()
	game.dealer.AddCard(pCard)
	game.DealerHit()
}

func (game *Game)getStatus() GameStatus {
	hideFirst := false
	if game.status == player_hit_hold {
		hideFirst = true
	}
	dealerCards := game.dealer.GetCards(hideFirst)
	playerCards := game.player.GetCards(false)

	return GameStatus{
		DealerCards: dealerCards,
		PlayerCards: playerCards,
		Message: game.getMessage(),
	}

}

func (game *Game)getMessage() string {
	return game.extra_msg + statusMap[game.status]
}