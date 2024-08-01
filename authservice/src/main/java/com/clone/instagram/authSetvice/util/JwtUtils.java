package com.clone.instagram.authSetvice.util;

import com.clone.instagram.authSetvice.Config.JwtConfig;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;



@Service
@Slf4j
public class JwtUtils {

    private final JwtConfig jwt;

    public JwtUtils(JwtConfig jwt) {
        this.jwt = jwt;
    }


    public  boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwt.getSecret().getBytes()).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateToken(Authentication authentication) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwt.getExpiration() * 1000L))
                .signWith(SignatureAlgorithm.HS256,jwt.getSecret())
                .compact();
    }

    public Claims getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwt.getSecret().getBytes())
                .parseClaimsJws(token).getBody();

    }
}