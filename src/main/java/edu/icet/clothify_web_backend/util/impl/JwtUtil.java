package edu.icet.clothify_web_backend.util.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String SECRET_KEY = "secret";

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String email, Object userData) {
        return createToken(email, userData);
    }

    private String createToken(String subject, Object userData) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("data", userData);
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(userDetails)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public Boolean validateToken(String token, String email) {
        final String extractedUsername = extractUserEmail(token);
        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token is null or empty");
            return false;
        }

        // Validate token format
        if (token.chars().filter(ch -> ch == '.').count() != 2) {
            return false;
        }
        try {
            return (extractedUsername.equals(email) && !isTokenExpired(token));
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean validateTokenByToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            System.out.println("Token is null or empty");
            return false;
        }

        // Validate token format
        if (token.chars().filter(ch -> ch == '.').count() != 2) {
            return false;
        }
        try {
            return (!isTokenExpired(token));
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public Integer extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        Map<String, Object> userData = (Map<String, Object>) claims.get("data");
        return (Integer) userData.get("id");
    }
}
