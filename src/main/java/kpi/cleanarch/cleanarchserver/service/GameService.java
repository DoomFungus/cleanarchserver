package kpi.cleanarch.cleanarchserver.service;

import kpi.cleanarch.cleanarchserver.model.Game;
import kpi.cleanarch.cleanarchserver.model.User;

import java.security.Principal;
import java.util.Optional;

public interface GameService {
    Optional<Game> findGame(String user, Integer gameType);

    void removeUserFromQueueIfExists(String username);

    Optional<String> getOtherPlayer(int gameId, String username);
}
