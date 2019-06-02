package kpi.cleanarch.cleanarchserver.dao;

import kpi.cleanarch.cleanarchserver.model.Game;

public interface GameRepository {
    Game getById(int id);

    void add(Game item);

    void removeById(int id);
}
