package kpi.cleanarch.cleanarchserver.listeners;

import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class UserDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {
    private GameService gameService;

    @Autowired
    public UserDisconnectEventListener(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        gameService.removeUserFromQueueIfExists(sessionDisconnectEvent.getUser().getName());
    }
}
