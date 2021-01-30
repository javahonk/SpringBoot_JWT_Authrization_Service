package com.digital.realty.jwt.config.decoder;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtDecoder {

    /*@Autowired
    private PublicKey publicKey;*/

    @Value("${jwt.publicKey}")
    private String publicKey;

    public Claims getClaimsFromToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException, InvalidKeySpecException, NoSuchAlgorithmException {

        Claims claims = Jwts.parser()
                .setSigningKey(generatePublicKey())
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public PublicKey generatePublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec pubKeySpecX509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        return kf.generatePublic(pubKeySpecX509EncodedKeySpec);
    }
}
