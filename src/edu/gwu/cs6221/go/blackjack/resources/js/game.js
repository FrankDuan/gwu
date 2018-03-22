
var dealerCards = {};
var playerCards = {};

var messageBox;

var url = "http://" + location.hostname + ":8080";
var graph = new joint.dia.Graph;
var erd = joint.shapes.erd;

const PAPER_WIDTH = 1000;
const PAPER_HEIGHT = 400;

var url = "http://" + location.hostname + ":8080";
function newGame() {
    $.getJSON(url.concat('/game/start'), function(data) {
        //data is the JSON string
        showScreen(data);
    });
};

function playerHit() {
    $.getJSON(url.concat('player/hit'), function(data) {
        //data is the JSON string
        showScreen(data);
    });
};

function playerStand() {
    $.getJSON(url.concat('player/stand'), function(data) {
        //data is the JSON string
        showScreen(data);
    });
};

function showScreen(txt) {
    $('#output').prepend('<p>' + txt + '</p>');
};

var onStartNew = function() {
    var cell = new joint.shapes.basic.Rect(
        {
            position: {x: 200, y: 200},
            size: {width: 80, height: 30},
            attrs: {text: {text: "Start New"}}
        });
    /**/
    graph.addCell(cell);
}


var onHit = function() {
    var cell = new joint.shapes.basic.Rect(
        {
            position: {x: 200, y: 200},
            size: {width: 80, height: 30},
            attrs: {text: {text: "Hit"}}
        });
    /**/
    graph.addCell(cell);
}

var onStand = function() {
    var cell = new joint.shapes.basic.Rect(
        {
            position: {x: 200, y: 200},
            size: {width: 80, height: 30},
            attrs: {text: {text: "Stand"}}
        });
    /**/
    graph.addCell(cell);
}
$(function()  {

    var paper = new joint.dia.Paper({
        el: $('#paper-container'),
        width: PAPER_WIDTH,
        height: PAPER_HEIGHT,
        model: graph,
        gridSize: 1
    });

    messageBox = new joint.shapes.basic.Rect(
        {
            position: {x: 200, y: 200},
            size: {width: 200, height: 30},
            attrs: {text: {text: "Please Start New"}}
        });
    /**/
    graph.addCell(messageBox);

    button = document.getElementById('startNew')
    button.onclick = onStartNew

    button = document.getElementById('hit')
    button.onclick = onHit

    button = document.getElementById('stand')
    button.onclick = onStand
    //setInterval(function(){drawTopology()}, 10000);

});