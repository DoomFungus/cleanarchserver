package kpi.cleanarch.cleanarchserver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FindGameResponse {
    @JsonProperty("response")
    private String response;
}
