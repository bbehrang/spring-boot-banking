package com.app.services;

import com.app.models.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    private String secret = "secret";

    public TokenData generateToken(String email, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", password);
        String token = createToken(email, map);
        return TokenData.builder().expiration(extractExpired(token)).token(token).build();
    }

    private String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder().addClaims(claims).setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Date extractExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public Date issuedAt(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getIssuedAt();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }



}
