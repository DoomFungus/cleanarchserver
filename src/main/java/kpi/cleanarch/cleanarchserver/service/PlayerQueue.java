package kpi.cleanarch.cleanarchserver.service;

import java.util.Optional;

public interface PlayerQueue {
    Optional<String> getIfQueueIsNotEmpty(Integer gameType);

    void addToQueue(Integer gameType, String username);

    void removeUser(String username);
}
