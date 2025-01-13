package org.mendozaproject.security;




import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {

    private String secretKey = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities()); // Add roles to the claims

        return Jwts.builder()
                .setClaims(claims) // Add claims to the token
                .setSubject(userDetails.getUsername()) // Add the username as the subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token issued time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token expiration (10 hours)
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Sign with secret key
                .compact();
    }
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private  <T> T extractClaims(String token, Function<Claims , T> claimsResolver ){
        final Claims  claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSignKey()) // Use the signing key
                    .build()
                    .parseClaimsJws(token); // Validate the token
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token is invalid
        }
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
