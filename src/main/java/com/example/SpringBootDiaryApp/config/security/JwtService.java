package com.example.SpringBootDiaryApp.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwtSecret}")
        private String secretKey;
    @Value("${accessTokenExpiration}")
        private Long accessExpiration;
    @Value("${refreshTokenExpiration}")
        private Long refreshExpiration;



    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    public String generateAccessToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    private String generateToken(HashMap<String,Object> extraClaims, UserDetails userDetails) {
        return buildJwtToken(extraClaims, userDetails, accessExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return buildJwtToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildJwtToken(Map<String, Object> claims, UserDetails userDetails, Long expiration) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(expiration)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
