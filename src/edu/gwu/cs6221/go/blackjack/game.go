package main

import "strings"

const (
	game_status_none	= 0
	player_hit_hold		= 1
	dealer_hit			= 2
	play_succeed		= 3
	dealer_succeed		= 4
	none_succeed		= 4
)

type Game struct {
	cards 	*Cards
	player	*Player
	dealer  *Player
	status  int
}

func (game *Game)Init() {
	game.cards = new(Cards)
	game.cards.Init(4)
	game.player = new(Player)
	game.dealer = new(Player)
}

func (game *Game)Run() {
	switch game.status {
	case game_status_none:
		game.Start()
		break
	case player_hit_hold:
		game.PlayerHitHold()
		break
	default:
		break
	}
}

func (game *Game)Start() {
	//1. give dealer and every player two cards.
	card := game.cards.GetCard()
	game.player.AddCard(card)
	card = game.cards.GetCard()
	game.player.AddCard(card)

	card = game.cards.GetCard()
	game.dealer.AddCard(card)
	card = game.cards.GetCard()
	game.dealer.AddCard(card)

	//2. check if players have blackjack, check if dealer has blackjack,
	if game.player.card_sum == 21 && game.dealer.card_sum == 21 {
		game.status = none_succeed
	} else if game.player.card_sum == 21 {
		game.status = play_succeed
	} else if game.dealer.card_sum == 21 {
		game.status = dealer_succeed
	} else {
		game.status = player_hit_hold
	}
}

func (game *Game)PlayerHitHold() {
	//1. if player hold, go to dealer hit. else go on.
	answer := game.player.answer
	if strings.Contains(answer, "hold") {
		game.status = dealer_hit
		game.DealerHit()
		return
	}

	//2. if player hit, give player one card
	card := game.cards.GetCard()
	game.player.AddCard(card)

	//3. if player bust or blackjact, end game. else go on
	if game.player.card_sum == 21 {
		game.status = play_succeed
	} else if game.player.card_sum > 21 {
		game.status = dealer_succeed
	}

	//4. else: query player1: hit or hold?
	//keep query player
}

func (game *Game)DealerHit() {
	//1. if dealer less bigger than 17, judge, end. else go on.
	if game.dealer.card_sum > 21 {
		game.status = play_succeed
		return
	} else if game.dealer.card_sum >= 17 {
		if game.dealer.card_sum > game.player.card_sum {
			game.status = dealer_succeed
		} else if game.dealer.card_sum == game.player.card_sum {
			game.status = none_succeed
		} else {
			game.status = play_succeed
		}
		return
	}

	//2. give dealer one card. go to 1
	card := game.cards.GetCard()
	game.dealer.AddCard(card)
	game.DealerHit()
}