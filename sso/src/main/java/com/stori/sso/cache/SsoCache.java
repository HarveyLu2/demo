package com.stori.sso.cache;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Harvey Lu
 * @date 2022/11/07 10:30
 **/
public class SsoCache {
    private static Map<String, String> userSessionMap = new ConcurrentHashMap();

    private static Map<String, String> codeMap = new ConcurrentHashMap<>();

    public static void putSession(String user, String session){
        userSessionMap.put(user, session);
    }

    public static String getSession(String user){
        return userSessionMap.get(user);
    }

    public static void deleteSession(String user){
        userSessionMap.remove(user);
    }

    public static void putCode(String user, String code){
        codeMap.put(user, code);
    }

    public static String getCode(String user){
        return codeMap.get(user);
    }

    public static void deleteCode(String user){
        codeMap.remove(user);
    }
}