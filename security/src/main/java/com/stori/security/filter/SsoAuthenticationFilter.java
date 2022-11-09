package com.stori.security.filter;
/*
    Copyright (c) Powerup Technology Inc. All rights reserved.
*/

import com.alibaba.fastjson.JSONObject;
import com.stori.security.cache.ClientCache;
import com.stori.security.utils.PropertiesFileUtil;
import com.stori.security.utils.RequestParameterUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import sun.net.www.http.HttpClient;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Harvey Lu
 * @date 2022/11/03 16:21
 **/
@Slf4j
public class SsoAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        //判断请求类型
        String type = PropertiesFileUtil.getInstance("sso-config").get("type");
        session.setAttribute("TYPE", type);
        if("client".equals(type)) {
            return validateClient(request, response);
        }
        if("server".equals(type)) {
            return subject.isAuthenticated();
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        StringBuffer ssoServerUrl = new StringBuffer(PropertiesFileUtil.getInstance("sso-config").get("sso.server.url"));
        //server需要登录
        String type = PropertiesFileUtil.getInstance("sso-config").get("type");
        if("server".equals(type)) {
            WebUtils.toHttp(servletResponse).sendRedirect(ssoServerUrl.append("/index").toString());
            return false;
        }

        //client登录校验
        ssoServerUrl.append("/index").append("?").append("appid").append("=").append(PropertiesFileUtil.getInstance("sso-config").get("app.name"));
        //回跳地址
        HttpServletRequest httpServletRequest = WebUtils.toHttp(servletRequest);
        StringBuffer backUrl = httpServletRequest.getRequestURL();
        String queryString = httpServletRequest.getQueryString();
        if(StringUtils.isNotBlank(queryString)) {
            backUrl.append("?").append(queryString);
        }
        ssoServerUrl.append("&").append("backUrl").append("=").append(URLEncoder.encode(backUrl.toString(), "utf-8"));
        WebUtils.toHttp(servletResponse).sendRedirect(ssoServerUrl.toString());
        return false;
    }

    /**
     * 认证中心登录成功带回code
     * 只有从会话会经过这个方法
     */
    private boolean validateClient(ServletRequest request, ServletResponse response) {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        //判断局部会话是否登录
        try{
            Session cacheClientSession = ClientCache.getSession("SHIRO_SESSION_CLIENT-" + sessionId);
            if(null != cacheClientSession) {
                //移除url中的code参数
                if(null != request.getParameter("code")){
                    String backUrl = RequestParameterUtil.getParameterWithOutCode(WebUtils.toHttp(request));
                    HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                    try {
                        httpServletResponse.sendRedirect(backUrl);
                    } catch (IOException e) {
                        log.error("局部会话已登录,移除code参数跳转出错:", e);
                    }
                }  else {
                    return true;
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        // 判断是否有认证中心code
        String code = request.getParameter("code");
        // 已拿到code
        if(StringUtils.isNotBlank(code)) {
            ClientCache.putSession("SHIRO_SESSION_CLIENT-" + sessionId, session);
            return true;
            //HttpPost去校验code
//            try {
//                StringBuffer ssoServerUrl = new StringBuffer(PropertiesFileUtil.getInstance("sso-config").get("sso.server.url"));
//                DefaultHttpClient httpClient = new DefaultHttpClient();
//                HttpPost httpPost = new HttpPost(ssoServerUrl.toString() + "/sso/code");
//
//                List<NameValuePair> nameValuePairs = new ArrayList<>();
//                nameValuePairs.add(new BasicNameValuePair("code", code));
//                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                HttpResponse httpResponse = httpClient.execute(httpPost);
//                if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                    HttpEntity httpEntity = httpResponse.getEntity();
//                    JSONObject result = JSONObject.parseObject(EntityUtils.toString(httpEntity));
//                    if(1 == result.getIntValue("code") && result.getString("data").equals(code)){
//                        // 返回请求资源
//                        try {
//                            // 移除url中的token参数(此处会导致验证通过后，仍然要进行一次验证，不过如果去掉的话，将会暴露pmi_code参数，影响安全性，暂无其他方案，先搁置)
//                            String backUrl = RequestParameterUtil.getParameterWithOutCode(WebUtils.toHttp(request));
//                            HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//                            httpServletResponse.sendRedirect(backUrl);
//                            return true;
//                        } catch (IOException e) {
//                            log.error("已拿到code，移除code参数跳转出错：", e);
//                        }
//                    } else {
//                        log.warn(result.getString("data"));
//                    }
//                }
//            } catch (IOException e) {
//                log.error("验证token失败：", e);
//            }
        }
        return false;
    }

}