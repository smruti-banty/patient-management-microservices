package com.pm.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
  private final Key secretKey;

  public JwtUtil(@Value("${jwt.secret}") String secret) {
    byte[] keyByte = Base64.getDecoder().decode(
        secret.getBytes(StandardCharsets.UTF_8));
    this.secretKey = Keys.hmacShaKeyFor(keyByte);
  }

  public String generateToken(String email, String role) {
    return Jwts.builder()
        .subject(email)
        .claim("role", role)
        .issuedAt(new Date())
        .expiration(Date.from(Instant.now().plus(Duration.ofHours(10))))
        .signWith(secretKey)
        .compact();
  }

  public void validateToken(String token) {
    try {
      Jwts.parser().verifyWith((SecretKey) secretKey)
          .build()
          .parseSignedClaims(token);
    } catch (SignatureException e) {
      throw new JwtException("Invalid JWT signature");
    } catch (JwtException e) {
      throw new JwtException("Invalid JWT");
    }
  }
}
