package ku.cs.restaurant.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import ku.cs.restaurant.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Set username as the token subject
                .claim("roles", roles) // Store roles in the token
                .setIssuedAt(new Date()) // Token issue time
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set token expiration
                .signWith(key(), SignatureAlgorithm.HS256) // Sign the token with the secret key
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        if (token.startsWith("Bearer "))
            token = token.substring(7).trim();

        return Jwts.parserBuilder()
                .setSigningKey(key()) // Set signing key for validation
                .build()
                .parseClaimsJws(token) // Parse token claims
                .getBody()
                .getSubject(); // Extract subject (username)
    }

    public Claims getClaims(String token) {
        if (token.startsWith("Bearer "))
            token = token.substring(7).trim();

        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private Key key() {
        byte[] keyBytes = jwtSecret.getBytes();

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("JWT secret key must be at least 256 bits (32 bytes) long.");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("roles", List.class);
    }
}
