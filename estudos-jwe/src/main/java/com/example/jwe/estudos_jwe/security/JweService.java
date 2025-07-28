package com.example.jwe.estudos_jwe.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class JweService {
    private final RSAKey rsaJWK;
    private final RSAEncrypter encrypter;
    private final RSADecrypter decrypter;
    
    public JweService() throws JOSEException {
        // Gerar par de chaves RSA para JWE
        this.rsaJWK = new RSAKeyGenerator(2048)
            .generate();
        this.encrypter = new RSAEncrypter(rsaJWK.toPublicJWK());
        this.decrypter = new RSADecrypter(rsaJWK.toPrivateKey());
    }
    
    public String encrypt(String payload) throws JOSEException {
        JWEObject jweObject = new JWEObject(
            new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                .build(),
            new Payload(payload)
        );
        
        jweObject.encrypt(encrypter);
        return jweObject.serialize();
    }
    
    public String decrypt(String encryptedPayload) throws JOSEException {
        JWEObject jweObject = null;
        try {
            jweObject = JWEObject.parse(encryptedPayload);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        jweObject.decrypt(decrypter);
        return jweObject.getPayload().toString();
    }
}