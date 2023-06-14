package dotseven.backend.service;

import dotseven.backend.security.TokenProvider;
import dotseven.backend.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

class TokenProviderTest {

    private TokenProvider tokenProvider;

    private final String secretKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDOKXduMc9IfTdNM4Pb5a3tawMjyKqlZaJSCYMSQDOGXNhW3XpHfGBMFElPwKXXTmKyJGiRRn/GwvDaqY+VgK+bhxdNQBVL6vlGgIImiP0/6vstCviGIE3WcymYn77BlWoeXeezkJow1dTbh9gTTOVSX8LJi6FQ0vrezCSRL8m/d24WwLR778EV67LiQfwKMKFbx3u7A9istt06RNLHUg8fUJiYrKkemCoSmsNtczHs6/N+8guek7fOwIr02/T51c7EU3CUu/0qF+u1ZQkxvqYmAeGqZ4RxGeSYyrF5AKWwIPJdZ81yl3j0zLg3q0PlyBGoitQz+X/jBZwACc4rVM3vAgMBAAECggEAITLzmkQyfBrl9UGuzJIvsFmY7NdGBlKCkYbEm64eXgHE44rQ7oLB7ckf29RLIIvtQCtgHMeR5gvDG4NOAvuby9uKThSxbpiilyQ0pp34IlCQguVkYL0CVolpu7vlzRNm6QhNj/tg89egv9AgD05FE5R7hVc98rUMVHqNXO/+zh0pZmVE4E1hd2I4JJKLU+GwK///d72YAPSNusWeF09JG4FCeWFEb/7fkYr1FUu7L9vJkAusMfFfbOjN4EqqEbAmikSMS5mw3/LM2J1Im6yFC+UuzOdP3swuVzYdU7+QDoZ/xDDirUxbZDyJl/hE4khwxci1ZpC4L09q/2Qt2HmBsQKBgQD9pGLVgYEDZjP2AKEmj2kNOfFMPjd71ZqvqQYVe99mI4wv3Nm2Z8d3GGr0Nat3hafehG4QfTdLGlT5+hgeoQIaIJbxvXKOCGqgHQ5KA5l+vGFICtJKxG85pLryAJ3o4twieGTwkfEa9cefBgRRc9xTF2NrVDKtgwBytEkXQJxo5wKBgQDQFBZ+CJWXDxA26DWg66vRvHymVjpsONgUhKfGg54u4xS1UlpWujIRsNAVlt0q7N2quI4dU2fTFiuS1C7sTqI7ZrQhG8GsJ5huYrxi3gmUn6ReB6zIx6WzKKwDs0rZX9kpIJhBsXHqOj2xrVmQEdChwJrmlVItEji1P0GT9oApuQKBgQDRbsT1CoPerKtG+2oVDuFtPGTT0aO6qruZQV0E9541e1RMAjMppB69DyL/Szh+giMocjH8LyRVOLJrgnwcH3t2O8KGKXYRXbVUmiUELjTfvTMqbLObbhxXmXGoV/CBmBCTir6wWbWHg+AbVVvdD1+lpdO7i0SPFNIYzje7Ei6DQKBgA6ybIhIaPB7guSt8CPGoaM10gQZsRS1yyVf3U3bPBAJwAuynmjjy+eN+pSbzFFc26dUU81kd7/kH/3F+O59+12AupZgFITpYw3g8Xd2QWz6/awalKy11VFbPRMh1daWu4r5H2f8dZRausLTALF5YKgcJ5TD1UMTt2et6R7fWVwZAoGAXaHTQ7INGDsHyC/t/LPvdXuqbXNG0At/vMmkEORb1tRkXc0gjbH80UPUy5NIxUPq/2OMjcINHiz1qq29DDj8nn7v5gblq3ELdE3pDk+nriIK1w2JV+oJLokyFR+c/C3iU6sQROBQahYKaN6gSt//dae1Mz/J+XMlMZitiN8pMtM=";
    private final int expirationMinutes = 10;

    @BeforeEach
    void setup() {

        // Arrange
        this.tokenProvider = new TokenProvider();

        ReflectionTestUtils.setField(tokenProvider, "secret", this.secretKey);
        ReflectionTestUtils.setField(tokenProvider, "expirationMinutes", Long.valueOf(this.expirationMinutes));
    }

    @Test
    void generateToken() {
        Authentication mockAuth = Mockito.mock(Authentication.class);
        UserDetails mockUserDetails= (UserDetails)new UserDetailsImpl("testPass", "testUname", List.of(new SimpleGrantedAuthority("USER")));
        when(mockAuth.getPrincipal()).thenReturn(mockUserDetails);

        // Act
        String responseToken = tokenProvider.generateToken(mockAuth);
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(this.secretKey.getBytes())
                .build()
                .parseClaimsJws(responseToken);

        // Assert
        assertEquals(jws.getBody().getSubject(), "testUname");
        assertEquals(jws.getBody().getExpiration().getTime() - jws.getBody().getIssuedAt().getTime(), this.expirationMinutes*60*1000);
    }

    @Test
    void validateToken_tokenOk() {

        // ArrangeString

        ZonedDateTime dateTime = ZonedDateTime.now();
        String tokenSubject = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(this.secretKey.getBytes()), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(dateTime.plusMinutes(expirationMinutes).toInstant()))
                .setIssuedAt(Date.from(dateTime.toInstant()))
                .setSubject("testUname")
                .claim("username", "testUname")
                .compact();

        // Act
        Optional<Jws<Claims>> responseClaims = tokenProvider.validateToken(tokenSubject);

        if(responseClaims.isEmpty()) {
            fail("Empty response Claims");
        }
        // Assert
        assertEquals(responseClaims.get().getBody().getSubject(), "testUname");
        assertEquals(responseClaims.get().getBody().get("username"), "testUname");
        assertEquals(responseClaims.get().getBody().getExpiration().getTime() - responseClaims.get().getBody().getIssuedAt().getTime(), this.expirationMinutes*60*1000);
    }

    @Test
    void validateToken_failExpired() {

        // ArrangeString

        ZonedDateTime dateTime = ZonedDateTime.now();
        String tokenSubject = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(this.secretKey.getBytes()), SignatureAlgorithm.HS512)
                .setIssuedAt(Date.from(dateTime.minusMinutes(2*expirationMinutes).toInstant()))
                .setExpiration(Date.from(dateTime.minusMinutes(expirationMinutes).toInstant()))
                .setSubject("testUname")
                .claim("username", "testUname")
                .compact();

        // Act
        Optional<Jws<Claims>> responseClaims = tokenProvider.validateToken(tokenSubject);

        // Assert
        assertEquals(responseClaims, Optional.empty());
    }
}