package com.stori.security.cache;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import org.apache.shiro.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Harvey Lu
 * @date 2022/11/07 14:41
 **/
public class ClientCache {

    private static Map<String, Session> codeSessionMap = new ConcurrentHashMap<>();


    public static void putSession(String user, Session code){
        codeSessionMap.put(user, code);
    }

    public static Session getSession(String user){
        return codeSessionMap.get(user);
    }

    public static void deleteSession(String user){
        codeSessionMap.remove(user);
    }
}