package com.assignment.transaction.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.function.Function;

@Component
public class JwtService {

    private SecretKey secretKey;
    private String secretKeyString = "ptDaB7iLYtpHSWe8Lf6LGYYIKDs4UpGT5XfUTmJ2bD4=";

    @PostConstruct
    public void init() {
        this.secretKey = new SecretKeySpec(secretKeyString.getBytes(), "HmacSHA256");
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    public String getBase64EncodedSecretKey() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    // Generate JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1))  // 12 jam exp
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))  // 12 jam exp
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String token, String username) {
        return username.equals(getUsernameFromToken(token)) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractClaim(token, Claims::getSubject);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    // Extract username from JWT Token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    // Extract expiration date from JWT Token
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    // Extract Claims from JWT Token
    public Claims getClaimsFromToken(String token) {
        var parser = Jwts.parser().verifyWith(secretKey).build();
        return  parser.parseSignedClaims(token).getPayload();

    }

    public boolean validateTokenWithBase64Key(String token, String username, String base64SecretKey) {
        SecretKey decodedSecretKey = decodeBase64ToSecretKey(base64SecretKey);
        return username.equals(getUsernameFromTokenWithKey(token, decodedSecretKey)) && !isTokenExpired(token);
    }

    // Decode Base64 encoded key back to SecretKey
    private SecretKey decodeBase64ToSecretKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
    }

    // Extract username using a specific secret key
    private String getUsernameFromTokenWithKey(String token, SecretKey secretKey) {
        var parser = Jwts.parser().verifyWith(secretKey).build();
        return  parser.parseSignedClaims(token).getPayload().getSubject();
    }
}
