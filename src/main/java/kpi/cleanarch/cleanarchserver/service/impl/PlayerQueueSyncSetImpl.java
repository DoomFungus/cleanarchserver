package kpi.cleanarch.cleanarchserver.service.impl;

import kpi.cleanarch.cleanarchserver.service.PlayerQueue;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayerQueueSyncSetImpl implements PlayerQueue {
    private Map<Integer, SortedSet<String>> waitingPlayers;

    public PlayerQueueSyncSetImpl() {
        this.waitingPlayers = new HashMap<>();
    }

    @Override
    public Optional<String> getIfQueueIsNotEmpty(Integer gameType) {
        String result = null;
        if(!waitingPlayers.containsKey(gameType))
            waitingPlayers.put(gameType, Collections.synchronizedSortedSet(new TreeSet<>()));
        SortedSet<String> playerQueue = waitingPlayers.get(gameType);
        if (!playerQueue.isEmpty()) {
            result = playerQueue.first();
            playerQueue.remove(result);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void addToQueue(Integer gameType, String username) {
        if(!waitingPlayers.containsKey(gameType))
            waitingPlayers.put(gameType, Collections.synchronizedSortedSet(new TreeSet<>()));
        waitingPlayers.get(gameType).add(username);
    }

    @Override
    public void removeUser(String username) {
        waitingPlayers.forEach((key, value) -> value.remove(username));
    }
}
