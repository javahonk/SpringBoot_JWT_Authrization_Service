package com.digital.realty.jwt.config.decoder;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component
public class JwtDecoder {

    @Autowired
    private PublicKey publicKey;

    /*@Autowired
    private PrivateKey privateKey;*/

    /*private String doGenerateToken(Map claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate("token"))
                .signWith(SignatureAlgorithm.RS512, privateKey)
                .compact();
    }*/

    public Claims getClaimsFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        Claims claims;

        claims = Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();


        return claims;
    }
}
