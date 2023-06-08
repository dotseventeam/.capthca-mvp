package dotseven.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import dotseven.backend.entity.User;

import javax.swing.text.html.Option;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Component
public class TokenProvider {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration.minutes}")
    private Long expirationMinutes;

    public String generateToken(Authentication authentication) {

        UserDetails user = (UserDetails) authentication.getPrincipal();

        byte[] tokenSigningKey = secret.getBytes();
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(tokenSigningKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expirationMinutes).toInstant()))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setSubject(user.getUsername())
                .claim("username", user.getUsername())
                .compact();
    }

    public Optional<Jws<Claims>> validateToken(String token) {

        try {
            byte[] tokenSigningKey = secret.getBytes();

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(tokenSigningKey)
                    .build()
                    .parseClaimsJws(token);

            return Optional.of(jws);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
