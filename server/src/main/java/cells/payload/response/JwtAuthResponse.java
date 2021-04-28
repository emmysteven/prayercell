package cells.payload.response;

public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}

/*
public class JwtAuthResponse {
     private Long id;
     private String email;
     private String role;
     private String type = "Bearer";
     private String token;


     public JwtAuthResponse(String token, Long id, String email, String role) {
         this.token = token;
         this.id = id;
         this.email = email;
         this.role = role;
     }
 }
*/
