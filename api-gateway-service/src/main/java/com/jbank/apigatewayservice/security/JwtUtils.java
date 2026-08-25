package com.jbank.apigatewayservice.security;

import com.jbank.apigatewayservice.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {


    private final JwtProperties jwtProperties;

    public boolean validate(String authToken) {
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

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Long.class);
    }
}
