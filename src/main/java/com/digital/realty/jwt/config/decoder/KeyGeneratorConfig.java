package com.digital.realty.jwt.config.decoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class KeyGeneratorConfig {

    @Value("${jwt.publicKey}")
    private String publicKey;

    @Bean
    public PublicKey generatePublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec pubKeySpecX509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        return kf.generatePublic(pubKeySpecX509EncodedKeySpec);
    }
}