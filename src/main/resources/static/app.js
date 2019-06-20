var stompClient = null;
var gameId;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#turns").html("");
}

function updateTurns(turn) {
    if (turn) {
        $("#turn-input").show();
    }
    else {
        $("#turn-input").hide();
    }
}

function connect() {
	fetch('http://localhost:80/signin', {
		method: 'POST'}
		)
        .then(function (response) { return response.json() })
        .then(function (data){
            var socket = new SockJS('http://localhost:80/stomp?token='+data.token);
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                console.log(stompClient.ws._transport.url);
                stompClient.subscribe('/user/'+parseJwt(data.token).sub+'/queue/game', function (game) {
                    gameId = (JSON.parse(game.body)).response;
                    updateTurns((JSON.parse(game.body)).turn_order === 0);
                });
                stompClient.subscribe('/user/'+parseJwt(data.token).sub+'/queue/game/turn', function (turn){
                    updateTurns(true);
                    onTurn("Opponent: ", JSON.parse(turn.body).turn);
                });
            })
        });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendStart() {
    stompClient.send("/app/game/start", {}, JSON.stringify({game_type:0}));
}

function sendTurn(sentTurn) {
    stompClient.send("/app/game/turn", {}, JSON.stringify({game_id:gameId, turn:sentTurn}));
    onTurn("Me: ", sentTurn);
    updateTurns(false);
}

function onTurn(startString, turn){
    $("#turns").append("<div class=\"row\">"+startString+turn+"</div>")
}

function parseJwt (token) {
    console.log(token.toString());
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#StartGame" ).click(function() { sendStart(); });
    $("#sendTurn").click(function () {sendTurn($("#turn").val());});
});