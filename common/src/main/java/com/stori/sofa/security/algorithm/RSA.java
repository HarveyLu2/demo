package com.stori.sofa.security.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class RSA {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSA.class);

    private static final int KEY_SIZE = 512;

    private static final String KEY_ALGORITHM = "RSA";

    private static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    public static void generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom sr = new SecureRandom();
        kpg.initialize(KEY_SIZE, sr);
        KeyPair kp = kpg.generateKeyPair();
        String pub = new String(Base64.getEncoder().encode(kp.getPublic().getEncoded()), StandardCharsets.UTF_8);
        String prv = new String(Base64.getEncoder().encode(kp.getPrivate().getEncoded()), StandardCharsets.UTF_8);
        System.out.println("publicKey:" + pub);
        System.out.println("privateKey:" + prv);
    }

    public static String encrypt(String source, String publicKey) {
        try {
            Key key = getPublicKey(publicKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] sourceBytes = source.getBytes();
            byte[] encryptBytes = cipher.doFinal(sourceBytes);
            return new String(Base64.getEncoder().encode(encryptBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Encrypt failed", e);
        }
    }

    public static String decrypt(String crypto, String privateKey) {
        try {
            Key key = getPrivateKey(privateKey);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cryptoBytes = Base64.getDecoder().decode(crypto.getBytes());
            byte[] decryptBytes = cipher.doFinal(cryptoBytes);
            return new String(decryptBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decrypt failed", e);
        }
    }

    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(key.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static String sign(String content, String privateKey) {
        String charset = StandardCharsets.UTF_8.toString();
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(privateKey.getBytes()));
            KeyFactory keyf = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes(charset));
            byte[] signed = signature.sign();
            return new String(Base64.getEncoder().encode(signed));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public static boolean verify(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            byte[] encodedKey = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature
                    .getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            boolean bverify = signature.verify(Base64.getDecoder().decode(sign));
            return bverify;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
