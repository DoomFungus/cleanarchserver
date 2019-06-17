package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

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
    public void findGame(Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "user/queue/game", principal.getName());
//        service.findGame(principal).ifPresent(x -> {
//
//        });
    }
}
