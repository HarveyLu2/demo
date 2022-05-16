package com.stori.sofa.security.algorithm;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSATest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RSATest.class);

    @Test
    public void testRsa() {
        String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiGDeM6AthJX+gX15pt9F2S/7FPaPZ2kxlKocxWtq4Oj2Dalx+61XrdVzdoUke3z1vMeDw66cQPPRbQn7bl8X5QIDAQABAkB9J+jDmAwt0TlvvKcX7W88kjvqURvp2zAVLsiYpKvBGrMVpIkWkgtvsUsuL2vuFNil5FKtuwgVk48RU/x9VawBAiEAwMWsIUzxhHcjnPIGy4TvynJ89Rn++4/Ma3w95PBxohECIQC1HAfO930X7CK08ePqGgeofH8HQ9A5ceeQ0yjJHvuElQIgQ0X4vxpSAs9tDz1rX6UB5d20e/jZgkL5rN0L4JSEo3ECIQCziMj404AvYcd6BjxASnODA39NbV0SRScf+yN4VH1TDQIgK0k7sc+gfEWzYv8TSoP9gJuuBdQrpVbj16w/xCKgEcU=";
        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhg3jOgLYSV/oF9eabfRdkv+xT2j2dpMZSqHMVrauDo9g2pcfutV63Vc3aFJHt89bzHg8OunEDz0W0J+25fF+UCAwEAAQ==";
        String content = "Stori-Dev..";
        LOGGER.info("source:" + content);
        String encrypt = RSA.encrypt(content, publicKey);
        LOGGER.info("encrypt:" + encrypt);
        String decrypt = RSA.decrypt(encrypt, privateKey);
        LOGGER.info("decrypt:" + decrypt);
        Assert.assertEquals("RSATest.testRsa() failed by not equals", content, decrypt);
        LOGGER.info("RSATest.testRsa() passed.");
    }

}