package com.focuszz.mkfs.web.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.focuszz.mkfs.authenticator.AccessAuthenticator;
import com.focuszz.mkfs.authenticator.Authenticator;
import com.focuszz.mkfs.common.utils.IdentifierGenerate;
import com.focuszz.mkfs.common.utils.IpUtils;
import com.focuszz.mkfs.common.utils.constant.Constants;
import com.focuszz.mkfs.common.utils.web.ApplicationContextWrapper;
import com.focuszz.mkfs.entity.LoginInfo;
import com.focuszz.mkfs.entity.User;
import com.focuszz.mkfs.service.LoginInfoService;
import com.focuszz.mkfs.service.UserService;
import com.focuszz.mkfs.service.impl.LoginInfoServiceImpl;
import com.focuszz.mkfs.service.impl.UserServiceImpl;
import com.focuszz.mkfs.web.context.WebContext;
import com.focuszz.mkfs.web.context.WebContextWrapper;
import com.foucszz.mkfs.common.utils.cookie.CookieWrapper;

public class LoginFilter extends OncePerRequestFilter {

    private LoginInfoService loginInfoService = ApplicationContextWrapper
        .getBean(LoginInfoServiceImpl.class);

    private UserService      userService      = ApplicationContextWrapper
        .getBean(UserServiceImpl.class);

    private Authenticator    authenticator    = ApplicationContextWrapper
        .getBean(AccessAuthenticator.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        WebContext context = WebContextWrapper.get();
        try {
            if (null != context)
                return;
            context = createWebContext(request, response);
            WebContextWrapper.set(context);
            String uri = request.getRequestURI();
            context.setRequestURI("".equals(uri) ? "/" : uri);
            //验证是否要进行登入
            if (!authenticator.authenticate(uri, context.isLogon())) {
                if (isXMLHttpRequest(request)) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"state\":\"" + Constants.STATE_FORBIDDEN + "\"}");
                } else {
                    String encodeURL = URLEncoder
                        .encode((uri + (StringUtils.isBlank(request.getQueryString()) ? ""
                            : "?" + request.getQueryString())), "UTF-8");
                    response.sendRedirect("/account/login.html?redirectURL=" + encodeURL);
                }

                return;
            }
            //验证是否有权限
            if (!authenticator.authenticate(uri, context.getRole())) {
                if (isXMLHttpRequest(request)) {
                    response.setContentType("application/json");
                    response.getWriter().write("{\"state\":\"" + Constants.STATE_FORBIDDEN + "\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.sendRedirect("/403.html");
                }
                return;
            }

        } catch (Exception e) {

        }
    }

    private WebContext createWebContext(HttpServletRequest request, HttpServletResponse response) {
        WebContext context = new WebContext();
        context.setIp(IpUtils.getIpAddress(request));
        CookieWrapper cookieWrapper = new CookieWrapper(request.getCookies(),
            Constants.COOKIE_DOMAIN);
        String sid = cookieWrapper.getCookieValue(Constants.COOKIE_SESSION_ID);
        context.setSid(sid);
        if (StringUtils.isBlank(sid)) {
            sid = IdentifierGenerate.generateSid(context.getIp(),
                Constants.IDENTIFY_TYPE_SESSION_ID);
            cookieWrapper.addCookie(response, Constants.COOKIE_SESSION_ID, sid, 365 * 24 * 3600);
            context.setSid(sid);
            return context;
        }

        String cid = cookieWrapper.getCookieValue(Constants.COOKIE_CONTEXT_ID);
        final ContextIdWrapper contextIdWrapper = new ContextIdWrapper(cid);
        if (!contextIdWrapper.isValid()) {
            return context;
        }

        LoginInfo loginInfo = loginInfoService.findById(contextIdWrapper.getloginId());
        if (loginInfo == null || loginInfo.getUserId() != contextIdWrapper.getUserId()) {
            return context;
        }

        context.setLoginInfo(loginInfo);
        User user = userService.findById(loginInfo.getUserId());
        if (user != null) {
            context.setLogon(true);
            context.setUser(user);
        }
        return context;
    }

    static class ContextIdWrapper {

        private Object[] contextIdSplits;

        public ContextIdWrapper(String cid) {
            init(cid);
        }

        public int getloginId() {
            return (int) contextIdSplits[0];
        }

        public int getUserId() {
            return (int) contextIdSplits[1];
        }

        public String getUserName() {
            return String.valueOf(contextIdSplits[2]);
        }

        public boolean isValid() {
            return contextIdSplits != null;
        }

        public void init(String cid) {
            String[] cidSplits = StringUtils.trimToEmpty(cid).split(":");
            if (cidSplits.length == 3) {
                contextIdSplits = new Object[3];
                try {
                    contextIdSplits[0] = Integer.parseInt(cidSplits[0]);
                    contextIdSplits[1] = Integer.parseInt(cidSplits[1]);
                    contextIdSplits[2] = cidSplits[2];
                } catch (Exception e) {
                    contextIdSplits = null;
                }
            }
        }
    }

    private boolean isXMLHttpRequest(HttpServletRequest request) {
        return StringUtils.equalsIgnoreCase(request.getHeader("X-Requested-With"),
            "XMLHttpRequest");
    }

}
