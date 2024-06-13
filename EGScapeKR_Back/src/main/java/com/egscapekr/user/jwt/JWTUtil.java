package com.egscapekr.user.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey secretAccessKey;
    private final SecretKey secretRefreshKey;

    public JWTUtil(@Value("${spring.jwt.secret-access}") String secretAccess, @Value("${spring.jwt.secret-refresh}") String secretRefresh) {
        this.secretAccessKey = new SecretKeySpec(secretAccess.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        this.secretRefreshKey = new SecretKeySpec(secretRefresh.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(secretAccessKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRoleFromToken(String token) {
        return Jwts.parser().verifyWith(secretAccessKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    /**
     * type true: AccessToken
     * false: RefreshToken
     */
    public Boolean isTokenExpired(String token, boolean type) {
        try {
            if (type)
                return Jwts.parser().verifyWith(secretAccessKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
            else
                return Jwts.parser().verifyWith(secretRefreshKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    public String createAccessToken(String username, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretAccessKey)
                .compact();
    }

    public String createRefreshToken(Long expiredMs) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretRefreshKey)
                .compact();
    }
}
