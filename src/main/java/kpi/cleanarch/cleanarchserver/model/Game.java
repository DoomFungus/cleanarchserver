package kpi.cleanarch.cleanarchserver.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    public final Integer gameId;
    private List<String> users;
    private int currentUserTurn;

    public Game(Integer gameId, List<String> users) {
        this.gameId = gameId;
        this.users = Collections.unmodifiableList(users);
        currentUserTurn = 0;
    }

    public boolean isCurrentUser(String username){
        return users.get(currentUserTurn).equals(username);
    }

    public void makeTurn() {
        currentUserTurn = currentUserTurn==users.size()-1?0:currentUserTurn+1;
    }

    public List<String> getAllPlayersExceptSent(String username){
        return users.stream().filter(x -> !x.equals(username)).collect(Collectors.toList());
    }

    public List<String> getAllPlayers(){
        return new ArrayList<>(users);
    }
}
