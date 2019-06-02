package kpi.cleanarch.cleanarchserver.dao.impl;

import kpi.cleanarch.cleanarchserver.dao.GameRepository;
import kpi.cleanarch.cleanarchserver.model.Game;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
@Scope("singleton")
public class GameRepositoryHashMap implements GameRepository {
    private ConcurrentHashMap<Integer, Game> games;

    @Override
    public Game getById(int id) {
        return games.get(id);
    }

    @Override
    public void add(Game item) {
        games.put(item.GameId, item);
    }

    @Override
    public void removeById(int id) {
        games.remove(id);
    }
}
