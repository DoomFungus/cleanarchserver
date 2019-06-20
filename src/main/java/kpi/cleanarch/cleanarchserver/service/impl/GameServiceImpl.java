package kpi.cleanarch.cleanarchserver.service.impl;

import kpi.cleanarch.cleanarchserver.repository.GameRepository;
import kpi.cleanarch.cleanarchserver.model.Game;
import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;

    private Map<Integer, SortedSet<String>> waitingPlayers;

    private AtomicInteger gameIdSequence;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        waitingPlayers = new HashMap<>();
        gameIdSequence = new AtomicInteger(-1);
    }

    public Optional<Game> findGame(String user, Integer gameType){
        Game game = null;
        if(!waitingPlayers.containsKey(gameType))
            waitingPlayers.put(gameType, Collections.synchronizedSortedSet(new TreeSet<>()));
        Optional<String> opponent = findOpponent(user, gameType);
        if(opponent.isPresent()){
            game = new Game(gameIdSequence.incrementAndGet(), Arrays.asList(user, opponent.get()));
            gameRepository.add(game);
        }
        return Optional.ofNullable(game);
    }

    private Optional<String> findOpponent(String user, Integer gameType){
        String result = null;
        SortedSet<String> playerQueue = waitingPlayers.get(gameType);
        if (playerQueue.isEmpty()) playerQueue.add(user);
        else if (!playerQueue.first().equals(user)) {
            result = playerQueue.first();
            playerQueue.remove(result);}
        return Optional.ofNullable(result);
    }

    @Override
    public void removeUserFromQueueIfExists(String username) {
        waitingPlayers.forEach((key, value) -> value.remove(username));
    }

    @Override
    public Optional<String> getOtherPlayer(int gameId, String username) {
        String user = null;
        Optional<Game> game = gameRepository.getById(gameId);
        if(game.isPresent()){
            if(game.get().makeTurnIfCurrentUser(username)){
                user = game.get().getUserWhoseTurn();
            }
        }
        return Optional.ofNullable(user);
    }
}
