package com.stori.sso.controller;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.stori.sso.cache.SsoCache;
import com.stori.sso.constant.GoogleConstant;
import com.stori.sso.model.GoogleAccountInfo;
import com.stori.sso.service.GoogleOauthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Harvey Lu
 * @date 2022/11/07 10:18
 **/
@Controller
@RequestMapping("/sso")
@Slf4j
public class SsoController {

    @Autowired
    GoogleOauthService googleOauthService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        // 检查用户是否已登录
        // ...

        // 已登录跳转重定向页面
        // ...
        String appId = request.getParameter("appid");
        String backUrl = request.getParameter("backUrl");
//        return "redirect:/sso/doLogin?backUrl=" + URLEncoder.encode(backUrl, "UTF-8");
        return "login";

    }

    @ResponseBody
    @RequestMapping("/googleIndex")
    public void googleIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // redirect to google for authorization
        StringBuilder oauthUrl = new StringBuilder().append("https://accounts.google.com/o/oauth2/auth")
                .append("?client_id=").append(GoogleConstant.CLIENT_ID)
                .append("&response_type=code")
                .append("&scope=openid%20email")
                .append("&redirect_uri=http://localhost:8085/sso/googleLogin")
                .append("&state=this_can_be_anything_to_help_correlate_the_response%3Dlike_session_id")
                .append("&access_type=offline")
                .append("&approval_prompt=force");
        response.sendRedirect(oauthUrl.toString());
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    public String doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("userPassword");
        if (StringUtils.isBlank(userName)) {
            return "帐号不能为空";
        }
        if (StringUtils.isBlank(password)) {
            return "密码不能为空";
        }

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        //判断是否已登录,如果已登录,则回跳,防止重复登录,同时需判断，是否为同一IP，如果不为同一IP,需删除原会话，重新登录
        String oldSessionId = SsoCache.getSession(userName);
        if (!StringUtils.isBlank(oldSessionId) && !sessionId.equals(oldSessionId)) {
            SsoCache.deleteSession(userName);
        }

        if (StringUtils.isBlank(oldSessionId) || (StringUtils.isNotBlank(oldSessionId) && !sessionId.equals(oldSessionId))) {
            // 使用Shiro认证登录
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
            subject.login(usernamePasswordToken);
            //更新session状态
            //全局会话sessionID列表,供会话管理
            SsoCache.putSession(userName, sessionId);
            //code校验值,目前以server的sessionId作为校验值
            SsoCache.putCode("SERVER_CODE-" + sessionId, sessionId);
        }

        //回跳登录前地址
        String backUrl = "http://localhost:8085/hello";
        if (StringUtils.isNotBlank(sessionId)) {
            if (backUrl.contains("?")) {
                backUrl += "&code=" + sessionId + "&username=" + userName;
            } else {
                backUrl += "?code=" + sessionId + "&username=" + userName;
            }
        }
        if (StringUtils.isBlank(backUrl)) {
            backUrl = request.getContextPath();
            response.sendRedirect(backUrl);
        } else {
            response.sendRedirect(backUrl);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/googleLogin")
    public void googleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, GeneralSecurityException {
        // redirect to google for authorization
        String code = request.getParameter("code");
        String queryStr = request.getQueryString();
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GoogleConstant.CLIENT_ID);
        params.put("client_secret", GoogleConstant.CLIENT_SECRET);
        params.put("redirect_uri", "http://localhost:8085/sso/googleLogin");
        params.put("grant_type", "authorization_code");
        String body = post("https://accounts.google.com/o/oauth2/token", params);

        JSONObject jsonObject = null;

        // get the access token from json and request info from Google
        try {
            jsonObject = JSONObject.parseObject(body);
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse json " + body);
        }
        // google tokens expire after an hour, but since we requested offline access we can get a new token without user involvement via the refresh token
//        String accessToken = (String) jsonObject.get("access_token");
//        String userJson = get(new StringBuilder("https://www.googleapis.com/oauth2/v1/userinfo?access_token=").append(accessToken).toString());
//        GoogleAccountInfo accountInfo = googleOauthService.parseJson(userJson);

        String idToken = (String) jsonObject.get("id_token");
        GoogleAccountInfo accountInfo2 = googleOauthService.parseIdToken(idToken);

        // you may want to store the access token in session
//        request.getSession().setAttribute("access_token", accessToken);

    }


    public String post(String url, Map<String, String> formParameters) throws ClientProtocolException, IOException {
        HttpPost request = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (String key : formParameters.keySet()) {
            nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
        }
        request.setEntity(new UrlEncodedFormEntity(nvps));

        return execute(request);
    }

    private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);

        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
        }

        return body;
    }


    @RequestMapping(value = "/code", method = RequestMethod.POST)
    @ResponseBody
    public Object code(HttpServletRequest request) {
        String codeParam = request.getParameter("code");
        String code = SsoCache.getCode("SERVER_CODE-" + codeParam);
        return !StringUtils.isBlank(codeParam) && codeParam.equals(code);
    }

    @ExceptionHandler({ShiroException.class})
    @ResponseBody
    public String handleException(ShiroException e) {
        String errMsg = "";
        if (e instanceof UnknownAccountException) {
            errMsg = "user name is not exist";
        } else if (e instanceof IncorrectCredentialsException) {
            errMsg = "user password is not correct";
        } else if (e instanceof AuthorizationException) {
            errMsg = "don't have permission";
        } else {
            errMsg = "system error";
        }
        return errMsg;
    }
}