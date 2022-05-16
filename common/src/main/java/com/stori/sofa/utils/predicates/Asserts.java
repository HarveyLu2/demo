package com.stori.sofa.utils.predicates;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class Asserts {

    private static Logger LOGGER = LoggerFactory.getLogger(Asserts.class);

    public static void requireEquals(String expected, String actual, String message) {
        if (!StringUtils.equals(expected, actual)) {
            LOGGER.error(message);
            throw new RuntimeException(message);
        }
    }

    private Asserts() {
    }
}
