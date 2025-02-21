package com.jorupmotte.donotdrink.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtProvider {

  @Value("${JWT_SECRET_KEY}")
  private String secretKey;

  public String createJwt(String userAccountId) {
    // valid for 1 hour
    Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); 
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    String jwt = Jwts.builder()
            .subject(userAccountId)
            .issuedAt(new Date())
            .expiration(expiredDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

    return jwt;
  }

  public String validateJwt(String jwt) {
    String subject = null;
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    try{
        subject = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getSubject();
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }

    return subject;

  }
}
