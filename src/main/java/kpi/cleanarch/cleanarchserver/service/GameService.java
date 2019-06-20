package kpi.cleanarch.cleanarchserver.service;

import kpi.cleanarch.cleanarchserver.model.Game;
import kpi.cleanarch.cleanarchserver.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface GameService {
    Optional<Game> findGame(String user, Integer gameType);

    void removeUserFromQueueIfExists(String username);

    List<String> onTurn(int gameId, String username);

    List<String> onEnd(int gameId, String username);

    void removeGame(int gameId);
}
