package com.stori.sofa.security.secrets;

import com.stori.sofa.security.algorithm.RSA;

/**
 * @author Daniel Yang(sw)
 * @version 20220525
 * @description Secrets manager for credentials.
 * @date 2022/5/7 14:19
 */
public final class SecretsManager {

    private static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIhg3jOgLYSV/oF9eabfRdkv+xT2j2dpMZSqHMVrauDo9g2pcfutV63Vc3aFJHt89bzHg8OunEDz0W0J+25fF+UCAwEAAQ==";

    private static final String PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiGDeM6AthJX+gX15pt9F2S/7FPaPZ2kxlKocxWtq4Oj2Dalx+61XrdVzdoUke3z1vMeDw66cQPPRbQn7bl8X5QIDAQABAkB9J+jDmAwt0TlvvKcX7W88kjvqURvp2zAVLsiYpKvBGrMVpIkWkgtvsUsuL2vuFNil5FKtuwgVk48RU/x9VawBAiEAwMWsIUzxhHcjnPIGy4TvynJ89Rn++4/Ma3w95PBxohECIQC1HAfO930X7CK08ePqGgeofH8HQ9A5ceeQ0yjJHvuElQIgQ0X4vxpSAs9tDz1rX6UB5d20e/jZgkL5rN0L4JSEo3ECIQCziMj404AvYcd6BjxASnODA39NbV0SRScf+yN4VH1TDQIgK0k7sc+gfEWzYv8TSoP9gJuuBdQrpVbj16w/xCKgEcU=";

    public static String decrypt(String encrypt) {
        return RSA.decrypt(encrypt, PRIVATE_KEY);
    }
    public static String encrypt(String source) {
        return RSA.encrypt(source, PUBLIC_KEY);
    }

    private SecretsManager() {
    }
}
