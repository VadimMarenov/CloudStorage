package ru.maren.cloudstorage.model.responce;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String authToken;

    @JsonProperty("auth-token")
    public String getAuthToken() {
        return authToken;
    }

}
