package com.llibron.projectplan.utilities.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.AESEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class JwtUtil {

    private static final RSAKey signingKeyPair = generateSigningKeyPair();
    private static final SecretKey encryptionKeyPair = generateEncryptionKeyPair();

    private static RSAKey generateSigningKeyPair() {
        try {
            RSAKey senderJWK = new RSAKeyGenerator(2048)
                    .keyID("123")
                    .keyUse(KeyUse.SIGNATURE)
                    .generate();

            return senderJWK;
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private static SecretKey generateEncryptionKeyPair() {
        int keyBitLength = EncryptionMethod.A256GCM.cekBitLength();

        // Generate key
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        keyGen.init(keyBitLength);
        return keyGen.generateKey();
    }

    public static SignedJWT createSignedJWT() throws JOSEException {
        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(signingKeyPair);

        // Prepare JWT with claims set
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("alice")
                .issuer("https://c2id.com")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000))
                .build();

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.RS256),
                claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        return signedJWT;
    }

    public static JWEObject encryptJWT(String signedJWT) throws JOSEException {
        // Create JWE object with signed JWT as payload
        JWEObject jweObject = new JWEObject(
                new JWEHeader(JWEAlgorithm.DIR, EncryptionMethod.A128GCM),
                new Payload(signedJWT));

        // Create an encrypter with the public key
        AESEncrypter encrypter = new AESEncrypter(encryptionKeyPair);

        // Encrypt the JWE
        jweObject.encrypt(encrypter);

        return jweObject;
    }

    public static String signAndEncryptJWT() throws JOSEException {
        SignedJWT signedJWT = createSignedJWT();
        return encryptJWT(signedJWT.serialize()).serialize();
    }
}
