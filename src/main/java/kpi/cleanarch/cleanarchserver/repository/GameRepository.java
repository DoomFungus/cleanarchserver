package kpi.cleanarch.cleanarchserver.repository;

import kpi.cleanarch.cleanarchserver.model.Game;

import java.util.Optional;

public interface GameRepository {
    Optional<Game> getById(int id);

    void add(Game item);

    void removeById(int id);
}
