package cells.application.payload.request;

import lombok.*;

@Data
public class JwtAuthenticationResponse {

    private String accessToken;

    private String refreshToken;

    private String tokenType;

    private Long expiryDuration;

    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiryDuration) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiryDuration = expiryDuration;
        tokenType = "Bearer ";
    }
}
