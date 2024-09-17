package com.gani.spring_security_cmplt.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.gani.spring_security_cmplt.model.AuthRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private final String SECRET = "hjqFPCwrBxpBj0fuU5yKHY9CRfd3oqTQCeImFHBxzYA=";
    // // secretkeycodingwallah ecrypted
    // private final String SECRET = "memonabdulganiirfan313maseeramemonbilalmemon";
    // // secretkeycodingwallah ecrypted

    public String generateToken(AuthRequest authRequest) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(authRequest.getUsername(), claims);

    }

    private String createToken(String userName, Map<String, Object> claims) {

        return Jwts.builder()
                .header().add(Map.of("alg", "HS256", "typ",
                        "JWT"))
                .and()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1))
                .signWith(getSignInKey())
                .compact();
        /*
         * 1000 ms * 60 s * 15 min
         */
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        // System.out.println(extractExpiration(token));
        // System.out.println(extractExpiration(token).before(new Date()));
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
