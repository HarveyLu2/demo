package com.stori.sso.service;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import com.alibaba.fastjson.JSONObject;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.stori.sso.constant.GoogleConstant;
import com.stori.sso.model.GoogleAccountInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * @author Harvey Lu
 * @date 2022/11/07 21:04
 **/
@Component
public class GoogleOauthService {

    public GoogleAccountInfo parseJson(String jsonStr) {
        GoogleAccountInfo accountInfo = new GoogleAccountInfo();
        if (jsonStr != null) {
            JSONObject jsonObj = JSONObject.parseObject(jsonStr);
            accountInfo.setUserId(jsonObj.getString("id"));
            accountInfo.setEmail(jsonObj.getString("email"));
        } else {
            System.out.println("Invalid Json.");
        }
        return accountInfo;
    }

    public GoogleAccountInfo parseIdToken(String idTokenStr) throws GeneralSecurityException, IOException {
        GoogleAccountInfo accountInfo = new GoogleAccountInfo();
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList(GoogleConstant.CLIENT_ID))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenStr);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            accountInfo.setUserId(payload.getSubject());
            accountInfo.setEmail(payload.getEmail());
            accountInfo.setEmailVerified(payload.getEmailVerified());
            accountInfo.setHostDomain(payload.getHostedDomain());

        } else {
            System.out.println("Invalid ID token.");
        }

        return accountInfo;
    }
}