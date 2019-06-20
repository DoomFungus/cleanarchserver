package kpi.cleanarch.cleanarchserver.repository.impl;

import kpi.cleanarch.cleanarchserver.repository.GameRepository;
import kpi.cleanarch.cleanarchserver.model.Game;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope("singleton")
public class GameRepositoryHashMap implements GameRepository {
    private static ConcurrentHashMap<Integer, Game> games = new ConcurrentHashMap<>();

    @Override
    public Optional<Game> getById(int id) {
        return Optional.ofNullable(games.get(id));
    }

    @Override
    public void add(Game item) {
        games.put(item.gameId, item);
    }

    @Override
    public void removeById(int id) {
        games.remove(id);
    }
}
