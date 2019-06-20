package kpi.cleanarch.cleanarchserver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EndRequest {
    @JsonProperty("game_id")
    private Integer gameId;
    @JsonProperty("end_type")
    private Integer endType;
}
