package com.company.base.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret}") private String secretKey;
    @Value("${jwt.legacy-secret:}") private String legacySecretKey;
    @Value("${jwt.expiration}") private long jwtExpiration;

    public String extractUsername(String token) { return extractClaim(token, Claims::getSubject); }
    public <T> T extractClaim(String token, Function<Claims, T> resolver) { final Claims claims = extractAllClaims(token); return resolver.apply(claims); }
    public String generateToken(UserDetails userDetails) { return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + jwtExpiration)).signWith(getSignInKey(), Jwts.SIG.HS512).compact(); }
    public boolean isTokenValid(String token, UserDetails userDetails) { return (extractUsername(token).equals(userDetails.getUsername())) && !isTokenExpired(token); }
    private boolean isTokenExpired(String token) { return extractClaim(token, Claims::getExpiration).before(new Date()); }

    private Claims extractAllClaims(String token) {
        try {
            return parseClaims(token, getSignInKey());
        } catch (JwtException ex) {
            if (StringUtils.hasText(legacySecretKey)) {
                return parseClaims(token, getLegacySignInKey());
            }
            throw ex;
        }
    }

    private Claims parseClaims(String token, SecretKey key) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() { return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)); }
    private SecretKey getLegacySignInKey() { return Keys.hmacShaKeyFor(Decoders.BASE64.decode(legacySecretKey)); }
}
