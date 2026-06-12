package com.jbank.authservice.security.jwt;

import com.jbank.authservice.entity.User;
import com.jbank.authservice.properties.SecurityJwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final SecurityJwtProperties jwtProperties;

    public String generateJwtToken(User user) {
        return generateTokenFromUsername(user.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().subject(username)
                .issuedAt(new Date()).expiration(new Date(new Date().getTime() + jwtProperties.getTokenExpiration().toMillis()))
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .compact();
    }

    public String getUsername(String token) {
       return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean  validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).build().parse(authToken);
            return true;
        } catch (SignatureException e){
            log.error("Invalid JWT signature" + e.getMessage());
        } catch (MalformedJwtException e){
            log.error("Invalid JWT token" + e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("Expired JWT token" + e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("Unsupported JWT token" + e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empty" + e.getMessage());
        }
        return false;
    }
}
