package kpi.cleanarch.cleanarchserver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FindGameRequest {
    @JsonProperty("game_type")
    private Integer gameType;
}
