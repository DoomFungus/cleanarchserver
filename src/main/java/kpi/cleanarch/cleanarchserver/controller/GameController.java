package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.messages.FindGameRequest;
import kpi.cleanarch.cleanarchserver.messages.FindGameResponse;
import kpi.cleanarch.cleanarchserver.messages.TurnMessage;
import kpi.cleanarch.cleanarchserver.security.JwtProvider;
import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@Controller
@MessageMapping(value = "/game")
public class GameController {
    private GameService service;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GameController(GameService service, SimpMessagingTemplate simpMessagingTemplate) {
        this.service = service;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(value = "/start")
    public void findGame(Principal principal, FindGameRequest request){
        service.findGame(principal.getName(), request.getGameType()).ifPresent(x -> {
            simpMessagingTemplate.convertAndSendToUser(x.User1,
                    "queue/game", new FindGameResponse(x.GameId, 1));
            simpMessagingTemplate.convertAndSendToUser(x.User2,
                    "queue/game", new FindGameResponse(x.GameId, 2));
        });
    }

    @MessageMapping(value = "/turn")
    public void takeTurn(Principal principal, TurnMessage turnMessage){
        service.getOtherPlayer(turnMessage.getGameId(), principal.getName())
                .ifPresent(x -> simpMessagingTemplate.convertAndSendToUser(x,
                                "queue/game/turn", turnMessage));
    }
}
