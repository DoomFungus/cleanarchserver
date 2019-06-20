package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.messages.EndRequest;
import kpi.cleanarch.cleanarchserver.messages.FindGameRequest;
import kpi.cleanarch.cleanarchserver.messages.FindGameResponse;
import kpi.cleanarch.cleanarchserver.messages.TurnMessage;
import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

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
            List<String> recipients = x.getAllPlayers();
            for (int i = 0; i < recipients.size(); i++) {
                simpMessagingTemplate.convertAndSendToUser(recipients.get(i),
                        "queue/game", new FindGameResponse(x.gameId, i));
            }
        });
    }

    @MessageMapping(value = "/turn")
    public void takeTurn(Principal principal, TurnMessage turnMessage){
        service.onTurn(turnMessage.getGameId(), principal.getName())
                .forEach(x -> simpMessagingTemplate.convertAndSendToUser(
                        x,"queue/game/turn", turnMessage));
    }

    @MessageMapping(value = "/end")
    public void endGame(Principal principal, EndRequest endRequest){
        service.onEnd(endRequest.getGameId(), principal.getName())
                .forEach(x -> simpMessagingTemplate.convertAndSendToUser(
                        x,"queue/game/end", endRequest));
        service.removeGame(endRequest.getGameId());
    }
}
