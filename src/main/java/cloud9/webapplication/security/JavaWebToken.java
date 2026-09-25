package cloud9.webapplication.security;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JavaWebToken {

    @Value("${app.jwt.secret:WelcomeToCloud9SoftTechnologies!!_LongerSecret}")
    private String jwtSecret;

    private SecretKey getSigningKey() {
        // Ensure the secret string is long enough for HS256 (at least 256 bits / 32 characters)
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSigningKey())
                .compact();
    }

    public Date getExpiryDateFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build().parseSignedClaims(token).getPayload()
                .getExpiration();
    }


    public boolean validateToken(String authToken) {
       // System.out.println(authToken);
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()) // 0.12.x way to verify
                    .build()
                    .parseSignedClaims(authToken);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiryDate = getExpiryDateFromJwtToken(token);
        return expiryDate.before(new Date());
    }



    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload() // 0.12.x uses getPayload() instead of getBody()
                .getSubject();
    }

}