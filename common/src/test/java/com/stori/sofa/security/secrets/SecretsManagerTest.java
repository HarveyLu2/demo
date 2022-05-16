package com.stori.sofa.security.secrets;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecretsManagerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretsManagerTest.class);

    @Test
    public void decrypt() {
        String source = "Stori-Dev..";
        String encrypt = "Y/jp2r0DUEINzAE95959t5PWbKfHuvPRaJuOwnxHnQOJXGntUrc3m/e8RkVXVZCCrakMgRg2cWVcRo30kwKg5A==";
        encrypt = "D4lBzyge7BInQOs7AZ3/JrKAYnNLBj/vBQGTgDgmyDaZSHMoK0HxlMB0fscgB8UdHM1xg+lP2nbyWu5cxHOryg==";
        String decrypt = SecretsManager.decrypt(encrypt);
        LOGGER.info("source:{}, decrypt:{}", source, decrypt);
        Assert.assertEquals("SecretsManager.decrypt(encrypt) failed, not equals.", source, decrypt);
        LOGGER.info("SecretsManager.decrypt(encrypt) passed.");
    }

    @Test
    public void encrypt() {
        String source = "Stori-Dev..";
        String encrypt = SecretsManager.encrypt(source);
        LOGGER.info("source:{}, encrypt:{}", source, encrypt);
        String decrypt = SecretsManager.decrypt(encrypt);
        Assert.assertEquals("SecretsManager.encrypt(source) failed, not equals.", source, decrypt);
        LOGGER.info("SecretsManager.encrypt(source) passed.");
    }
}