package kpi.cleanarch.cleanarchserver.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SignInResponse {
    @JsonProperty("token")
    private final String jwtToken;
}
