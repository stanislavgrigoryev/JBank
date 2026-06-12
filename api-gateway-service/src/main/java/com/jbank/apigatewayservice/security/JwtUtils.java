package com.jbank.apigatewayservice.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;


    public boolean validate(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).build().parse(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature" + e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token" + e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token" + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty" + e.getMessage());
        }
        return false;
    }
}
