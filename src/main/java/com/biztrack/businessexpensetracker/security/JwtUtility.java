package com.biztrack.businessexpensetracker.security;

import com.biztrack.businessexpensetracker.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtility {
    public Map<String, Object> mappingBodyToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Map<String, Object> map = new HashMap<>();
        map.put("email", claims.get("em"));
        // masih blm assign yg lain
        return map;
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        Long timeMilis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(timeMilis))
                .setExpiration(new Date(timeMilis + JwtConfig.getTimeExpiration()))
                .signWith(SignatureAlgorithm.HS512, JwtConfig.getSecretKey())
                .compact();
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
        /** Sudah otomatis tervalidaasi jika expired date masih aktif */
        String username = getUsernameFromToken(token);
        return (username != null && !isTokenExpired(token));
    }

}
