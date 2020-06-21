package com.romeiro.picklejar.config.security;

import com.romeiro.picklejar.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${picklejar.jwt.expiration}")
    private String expiration;

    @Value("${picklejar.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                    .setIssuer("PickleJar API")
                    .setSubject(user.getId().toString())
                    .setIssuedAt(today)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public Integer getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public String getFormattedToken(String token) {
        if (token == null || token.isEmpty() || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7, token.length());
    }

    public boolean isTokenExpired(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return false;
        }
        catch (Exception e) {
            return e.getMessage().startsWith("JWT expired");
        }
    }
}
