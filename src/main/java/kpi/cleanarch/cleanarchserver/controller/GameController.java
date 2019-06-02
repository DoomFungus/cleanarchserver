package kpi.cleanarch.cleanarchserver.controller;

import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping(value = "/game")
public class GameController {
    private GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }
}
