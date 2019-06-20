var stompClient = null;
var gameId;

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#StartGame" ).click(function() { sendStart(); });
    $("#sendTurn").click(function () {sendTurn($("#turn").val());});
    $("#EndGame").click(function () {sendEnd(0);});
});

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
                    $("#StartGame").prop("disabled", true);
                    $("#EndGame").prop("disabled", false);
                });
                stompClient.subscribe('/user/'+parseJwt(data.token).sub+'/queue/game/turn', function (turn){
                    updateTurns(true);
                    onTurn("Opponent: ", JSON.parse(turn.body).turn);
                });
                stompClient.subscribe('/user/'+parseJwt(data.token).sub+'/queue/game/end', function (end) {
                    onEnd("Opponent", JSON.parse(end.body).end_type);
                })
            })
        });
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#StartGame").prop("disabled", !connected);
    if(connected) $("#turns").html("");
}

function updateTurns(turn) {
     $("#sendTurn").prop("disabled", !turn);
}

function onTurn(startString, turn){
    $("#turns").append("<div class=\"row\">"+startString+turn+"</div>")
}

function onEnd(endString, endType){
    $("#turns").append("<div class=\"row\">"+endString+" ended game with end type"+endType+"</div>");
    $("#EndGame").prop("disabled", true);
    disconnect();
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

function sendEnd(end_type) {
    stompClient.send("/app/game/end", {}, JSON.stringify({game_id:gameId, end_type:end_type}));
    onEnd("You", 0);
}

function sendTurn(sentTurn) {
    stompClient.send("/app/game/turn", {}, JSON.stringify({game_id:gameId, turn:sentTurn}));
    onTurn("Me: ", sentTurn);
    updateTurns(false);
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

