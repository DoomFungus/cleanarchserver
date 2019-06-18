package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.messages.FindGameRequest;
import kpi.cleanarch.cleanarchserver.messages.FindGameResponse;
import kpi.cleanarch.cleanarchserver.model.User;
import kpi.cleanarch.cleanarchserver.security.JwtProvider;
import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@MessageMapping(value = "/game")
public class GameController {
    private JwtProvider jwtProvider;
    private GameService service;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public GameController(JwtProvider jwtProvider, GameService service, SimpMessagingTemplate simpMessagingTemplate) {
        this.jwtProvider = jwtProvider;
        this.service = service;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping(value = "/start")
    public void findGame(FindGameRequest request){
        service.findGame(request.getUsername()).ifPresent(x -> {
            simpMessagingTemplate.convertAndSendToUser(x.User1,
                    "queue/game", new FindGameResponse(x.GameId, 1));
            simpMessagingTemplate.convertAndSendToUser(x.User2,
                    "queue/game", new FindGameResponse(x.GameId, 2));
        });
    }
}
