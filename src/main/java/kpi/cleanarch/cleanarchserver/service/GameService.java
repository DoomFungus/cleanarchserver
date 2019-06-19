package kpi.cleanarch.cleanarchserver.service;

import kpi.cleanarch.cleanarchserver.model.Game;

import java.security.Principal;
import java.util.Optional;

public interface GameService {
    Optional<Game> findGame(String user);

    void removeUserFromQueueIfExists(String username);
}
