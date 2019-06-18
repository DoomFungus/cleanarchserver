package kpi.cleanarch.cleanarchserver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TurnMessage {
    @JsonProperty("game_id")
    private Integer gameId;
    @JsonProperty("turn")
    private String turn;
}
