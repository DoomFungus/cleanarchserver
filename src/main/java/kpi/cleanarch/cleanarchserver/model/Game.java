package kpi.cleanarch.cleanarchserver.model;

import lombok.AllArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
public class Game {
    public final int GameId;
    public final Principal User1;
    public final Principal User2;
}
