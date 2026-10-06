package com.onocia.gestionstage.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey cleSecrete = Keys.hmacShaKeyFor(
        "une-cle-secrete-suffisamment-longue-pour-etre-valide-en-production".getBytes()
    );

    private final long dureeValiditeMs = 1000 * 60 * 60 * 10; // 10 heures

    public String genererToken(String email) {
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + dureeValiditeMs))
            .signWith(cleSecrete)
            .compact();
    }

    public String extraireEmail(String token) {
        return extraireClaim(token, Claims::getSubject);
    }

    public boolean estValide(String token, String email) {
        String emailDuToken = extraireEmail(token);
        return emailDuToken.equals(email) && !estExpire(token);
    }

    private boolean estExpire(String token) {
        return extraireClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extraireClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
            .verifyWith(cleSecrete)
            .build()
            .parseSignedClaims(token)
            .getPayload();
        return resolver.apply(claims);
    }
}