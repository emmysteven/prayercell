package cells.security;

import cells.config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/** This class provides methods for generating, parsing, validating JWT
 * @Author Emmy Steven
 */

@Slf4j
@Component
public class JwtUtil {

    private SecretKeySpec secretKey;

    @Autowired
    private JwtConfig jwtConfig;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = jwtConfig.getSecret().getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA512");
    }

    public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());

        return Jwts.builder()
                .claim("name", userPrincipal.getName())
                .claim("email", userPrincipal.getEmail())
                .claim("username", userPrincipal.getUsername())
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {

        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        return Long.valueOf(jwsClaims.getBody().getSubject());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
