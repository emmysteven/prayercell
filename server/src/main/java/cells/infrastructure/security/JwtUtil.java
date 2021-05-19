package cells.security;

import cells.config.JwtConfig;
import cells.entities.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Date;

/** This class provides methods for generating, parsing, validating JWT
 * @Author Emmy Steven
 */

@Slf4j
@Component
public class JwtUtil {

    private SecretKeySpec secretKey;
    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @PostConstruct
    protected void init() {
        byte[] keyBytes = jwtConfig.getSecret().getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA512");
    }

    /**
     * Generates a token from a principal object. Embed the refresh token in the jwt
     * so that a new jwt can be created
     */
    public String generateToken(CustomUserDetails customUserDetails) {
        return generateTokenFromUserId(customUserDetails.getId());
    }

    /**
     * Generates a token from a principal object. Embed the refresh token in the jwt
     * so that a new jwt can be created
     */
    public String generateTokenFromUserId(Long userId) {
        Instant expiryDate = Instant.now().plusMillis(jwtConfig.getExpiration());
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiryDate))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Returns the user id encapsulated within the token
     */
    public Long getUserIdFromJWT(String token) {
        Jws<Claims> jwsClaims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        return Long.valueOf(jwsClaims.getBody().getSubject());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * Return the jwt expiration for the client so that they can execute
     * the refresh token logic appropriately
     */
    public Long getExpiryDuration() {
        return jwtConfig.getExpiration();
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
