package kpi.cleanarch.cleanarchserver.service;

import kpi.cleanarch.cleanarchserver.model.Game;

import java.util.Optional;

public interface GameService {
    Optional<Game> findGame(int userId);
}
