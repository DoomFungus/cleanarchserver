package kpi.cleanarch.cleanarchserver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FindGameResponse {
    @JsonProperty("response")
    private final Integer response;
    @JsonProperty("turn_order")
    private final Integer turnOrder;
}
