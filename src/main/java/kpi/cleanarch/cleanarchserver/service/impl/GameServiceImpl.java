package kpi.cleanarch.cleanarchserver.service.impl;

import kpi.cleanarch.cleanarchserver.repository.GameRepository;
import kpi.cleanarch.cleanarchserver.model.Game;
import kpi.cleanarch.cleanarchserver.service.GameService;
import kpi.cleanarch.cleanarchserver.service.PlayerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;
    private PlayerQueue playerQueue;
    private AtomicInteger gameIdSequence;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerQueue playerQueue) {
        this.gameRepository = gameRepository;
        this.playerQueue = playerQueue;
        gameIdSequence = new AtomicInteger(-1);
    }

    public Optional<Game> findGame(String user, Integer gameType){
        Game game = null;
        Optional<String> opponent = playerQueue.getIfQueueIsNotEmpty(gameType);
        if(opponent.isPresent()&&!opponent.get().equals(user)){
            game = new Game(gameIdSequence.incrementAndGet(), Arrays.asList(user, opponent.get()));
            gameRepository.add(game);
        }
        else playerQueue.addToQueue(gameType, user);
        return Optional.ofNullable(game);
    }

    @Override
    public void removeUserFromQueueIfExists(String username) {
        playerQueue.removeUser(username);
    }

    @Override
    public List<String> onTurn(int gameId, String username) {
        Optional<Game> game = gameRepository.getById(gameId);
        if(game.isPresent()){
            if(game.get().isCurrentUser(username)){
                game.get().makeTurn();
                return game.get().getAllPlayersExceptSent(username);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> onEnd(int gameId, String username) {
        Optional<Game> game = gameRepository.getById(gameId);
        if(game.isPresent()){
            return game.get().getAllPlayersExceptSent(username);
        }
        return new ArrayList<>();
    }

    @Override
    public void removeGame(int gameId) {
        gameRepository.removeById(gameId);
    }
}
