package kpi.cleanarch.cleanarchserver.service.impl;

import kpi.cleanarch.cleanarchserver.model.User;
import kpi.cleanarch.cleanarchserver.repository.GameRepository;
import kpi.cleanarch.cleanarchserver.model.Game;
import kpi.cleanarch.cleanarchserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class GameServiceImpl implements GameService {
    private GameRepository gameRepository;

    private LinkedBlockingQueue<String> waitingPlayers;

    private AtomicInteger gameIdSequence;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        waitingPlayers = new LinkedBlockingQueue<>();
        gameIdSequence = new AtomicInteger(-1);
    }

    public Optional<Game> findGame(String user){
        Game game = null;
        Optional<String> opponent = findOpponent(user);
        if(opponent.isPresent()){
            game = new Game(gameIdSequence.incrementAndGet(), user, opponent.get());
            gameRepository.add(game);
        }
        return Optional.ofNullable(game);
    }

    private Optional<String> findOpponent(String user){
        String result = null;
        if (waitingPlayers.isEmpty()) waitingPlayers.offer(user);
        else {
            try {
                 result = waitingPlayers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void removeUserFromQueueIfExists(String username) {
        waitingPlayers.remove(username);
    }

    @Override
    public Optional<String> getOtherPlayer(int gameId, String username) {
        String user = null;
        Optional<Game> game = gameRepository.getById(gameId);
        if(game.isPresent()){
            if(game.get().User1.equals(username)){
                user = game.get().User2;
            }
            if(game.get().User2.equals(username)){
                user = game.get().User1;
            }
        }
        return Optional.ofNullable(user);
    }
}
