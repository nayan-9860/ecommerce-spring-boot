package com.nayan.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRETE_KEY =
            "ecommerce_secret_key_which_should_be_very_long";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    private final Key key = Keys.hmacShaKeyFor(SECRETE_KEY.getBytes());

    public String generateToken(Long userId , String email , String role){

        return Jwts.builder()
                .setSubject(email)
                .claim("userId" , userId)
                .claim("role" , role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key , SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        return extractClaims(token).get("userId", Long.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

}
