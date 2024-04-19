package jovelAsirot.U5W3D5.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jovelAsirot.U5W3D5.entities.User;
import jovelAsirot.U5W3D5.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        Date currentDate = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .issuedAt(currentDate)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception ex) {
            throw new UnauthorizedException("Error with token, try again");
        }
    }

    public String extractIdFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(token).getPayload().getSubject();
    }
}
