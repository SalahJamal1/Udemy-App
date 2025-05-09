package com.app.udemy.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    public String extract_username(String token) {

        return extract_claim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extract_username(token);
        return username.equals(userDetails.getUsername()) && extract_claim(token, Claims::getExpiration).after(new Date());
    }

    private <T> T extract_claim(String token, Function<Claims, T> claimsFunction) {
        Claims claim = extract_claims(token);
        return claimsFunction.apply(claim);
    }

    private Claims extract_claims(String token) {
        return Jwts.parserBuilder().setSigningKey(get_key())
                .build().parseClaimsJws(token).getBody();
    }

    public String generate_token(UserDetails userDetails) {
        return generate_token(new HashMap<>(), userDetails);
    }

    public String generate_token(Map<String, Object> claim, UserDetails userDetails) {
        return Jwts.builder().signWith(get_key(), SignatureAlgorithm.HS256)
                .setClaims(claim)
                .setSubject(userDetails.getUsername())
                .setIssuer(Date.from(Instant.now()).toString())
                .setExpiration(Date.from(Instant.now().plus(90, ChronoUnit.DAYS)))
                .compact();

    }

    private Key get_key() {
        String secret_key = "es1mYJKDgynNHELYJgdJjDFqq3JmX8QskGwA/9gxAI0=";
        byte[] key = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(key);
    }
}
