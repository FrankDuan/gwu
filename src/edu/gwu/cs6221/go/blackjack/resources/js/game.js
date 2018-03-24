
var dealerCards = [];
var playerCards = [];
var message;
var elements = {};

var url = "http://" + location.hostname + ":8080";
var graph = new joint.dia.Graph;
var canvas;


var url = "http://" + location.hostname + ":8080";
var suits = ['clubs', 'diamonds', 'hearts', 'spades'];
var points = ['0', 'a', '2', '3', '4', '5', '6', '7', '8', '9', '10', 'j', 'q', 'k'];

var onStartNew = function() {
    $.getJSON(url.concat('/game/start'), function(data) {
        //data is the JSON string
        dealerCards = data['DealerCards']
        playerCards = data['PlayerCards']
        message = data['Message']
        displayElements()
    })
}

var onHit = function() {
    $.getJSON(url.concat('/player/hit'), function(data) {
        //data is the JSON string
        dealerCards = data['DealerCards']
        playerCards = data['PlayerCards']
        message = data['Message']
        displayElements()
    });
}

var onStand = function() {
    $.getJSON(url.concat('/player/stand'), function(data) {
        //data is the JSON string
        dealerCards = data['DealerCards']
        playerCards = data['PlayerCards']
        message = data['Message']
        displayElements()
    });
}

var displayElements = function() {
    //elements['message'].attr('text/text', 'Message:' + message)
    //var cards = ''
    //cards = cardsToString(playerCards)
    //elements['playerCards'].attr('text/text', 'Player cards: ' + cardsToString(playerCards))
    //elements['dealerCards'].attr('text/text', 'Dealer cards: ' + cardsToString(dealerCards))
    //var gameMessage = document.getElementById('gameMessage');
    document.getElementById('gameMessage').innerHTML = message;
    drawCards()
}

var cardsToString = function(cards) {
    var cardsStr = ''
    var length = cards.length
    for (var i = 0; i < length; i++) {
        cardsStr += '{Suit:' + cards[i].Suit + ', Value:' + cards[i].Value + '},'
    }
    return cardsStr
}

var drawCards = function() {
    canvas.clearRect(0, 0, 1000, 350);

    var length = dealerCards.length
    for (var i = 0; i < length; i++) {
        if (dealerCards[i].Value == 0) {
            canvas.drawPokerBack(100 + i * 150, 10, 150, '#5C72C2', '#2B4299');
        } else {
            canvas.drawPokerCard(100 + i * 150, 10, 150,
                suits[dealerCards[i].Suit], points[dealerCards[i].Value]);
        }
    }

    var length = playerCards.length
    for (var i = 0; i < length; i++) {
        canvas.drawPokerCard(100 + i * 150, 200, 150,
            suits[playerCards[i].Suit], points[playerCards[i].Value]);
    }
}

var initElements = function() {
    var container = document.getElementById('container1');
    var domCanvas = document.createElement('canvas');
    canvas = domCanvas.getContext('2d');
    container.appendChild(domCanvas);

    domCanvas.height = 350;
    domCanvas.width = 1000;

    /*
    var dealerCardsEle = new joint.shapes.basic.Rect(
        {
            position: {x: 100, y: 50},
            size: {width: 800, height: 50},
            attrs: {text: {
                    text: "Dealer cards:",
                    fill: '#152455',
                    'font-size': 18,
                    //'x-alignment': left,
                    'text-anchor': 'left',
                    'ref-x': 20
                }}
        });

    var playerCardsEle = new joint.shapes.basic.Rect(
        {
            position: {x: 100, y: 150},
            size: {width: 800, height: 50},
            attrs: {text: {
                    text: "Player cards:",
                    fill: '#152455',
                    'font-size': 18,
                    //'x-alignment': left,
                    'text-anchor': 'left',
                    'ref-x': 20
                }}
        });


    var messageBox = new joint.shapes.basic.Rect(
        {
            position: {x: 100, y: 280},
            size: {width: 800, height: 50},
            attrs: {text: {
                    text: "Please Start New",
                    fill: '#152455',
                    'font-size': 18,
                    //'x-alignment': left,
                    'text-anchor': 'left',
                    'ref-x': 20
                }}
        });

    graph.addCell(messageBox);
    graph.addCell(playerCardsEle);
    graph.addCell(dealerCardsEle);
    elements['message'] = messageBox
    elements['playerCards'] = playerCardsEle
    elements['dealerCards'] = dealerCardsEle
    */
}

$(function()  {
    /*
    var paper = new joint.dia.Paper({
        el: $('#paper-container'),
        width: PAPER_WIDTH,
        height: PAPER_HEIGHT,
        model: graph,
        gridSize: 1
    });*/

    var button1 = document.getElementById('startNew')
    button1.onclick = onStartNew

    var button2 = document.getElementById('hit')
    button2.onclick = onHit

    var button3 = document.getElementById('stand')
    button3.onclick = onStand

    initElements()
});