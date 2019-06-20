package kpi.cleanarch.cleanarchserver.model;

import java.util.Collections;
import java.util.List;

public class Game {
    public final Integer gameId;
    private List<String> users;
    private int currentUserTurn;

    public Game(Integer gameId, List<String> users) {
        this.gameId = gameId;
        this.users = Collections.unmodifiableList(users);
        currentUserTurn = 0;
    }

    public boolean makeTurnIfCurrentUser(String username) {
        if(users.get(currentUserTurn).equals(username)){
            currentUserTurn = currentUserTurn==users.size()-1?0:currentUserTurn+1;
            return true;
        }
        return false;
    }

    public String getUserWhoseTurn(){
        return users.get(currentUserTurn);
    }

    public String getPlayerByIndex(int index){
        return users.get(index);
    }

    public int getPlayerCount(){
        return users.size();
    }
}
