package kpi.cleanarch.cleanarchserver.service.impl;

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

    private LinkedBlockingQueue<Principal> waitingPlayers;

    private AtomicInteger gameIdSequence;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        waitingPlayers = new LinkedBlockingQueue<>();
    }

    public Optional<Game> findGame(Principal user){
        Game game = null;
        Optional<Principal> opponent = findOpponent(user);
        if(opponent.isPresent()){
            game = new Game(gameIdSequence.incrementAndGet(), user, opponent.get());
            gameRepository.add(game);
        }
        return Optional.of(game);
    }

    private Optional<Principal> findOpponent(Principal user){
        Principal result = null;
        if (waitingPlayers.isEmpty()) waitingPlayers.offer(user);
        else {
            try {
                 result = waitingPlayers.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Optional.of(result);
    }

}
