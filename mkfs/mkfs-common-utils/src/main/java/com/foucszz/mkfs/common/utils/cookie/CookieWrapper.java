package com.foucszz.mkfs.common.utils.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.focuszz.mkfs.common.utils.cookie.codec.Base64Codec;
import com.focuszz.mkfs.common.utils.cookie.codec.Codec;

public class CookieWrapper {

    public static final String DEFAULT_COOKIE_PATH = "/";

    private Cookie[]           cookies;

    private String             cookieDomain;

    private String             cookiePath;

    private Integer            cookieMaxAge;

    private Codec              codec;

    public CookieWrapper(Cookie[] cookies) {
        this(cookies, null);
    }

    public CookieWrapper(Cookie[] cookies, String cookieDomain) {
        this(cookies, cookieDomain, new Base64Codec());
    }

    public CookieWrapper(Cookie[] cookies, String cookieDomain, Codec codec) {
        this(cookies, cookieDomain, DEFAULT_COOKIE_PATH, codec);
    }

    public CookieWrapper(Cookie[] cookies, String cookieDomain, String cookiePath, Codec codec) {
        this(cookies, cookieDomain, DEFAULT_COOKIE_PATH, -1, codec);
    }

    public CookieWrapper(Cookie[] cookies, String cookieDomain, String cookiePath, int maxAge,
                         Codec codec) {
        this.cookies = cookies;
        this.cookieDomain = cookieDomain;
        this.cookiePath = cookiePath;
        this.cookieMaxAge = maxAge;
        this.codec = codec;
    }

    public String getCookieValue(String cookieName) {
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                String value = cookie.getValue();
                return codec == null ? value : codec.decode(value);
            }
        }
        return null;
    }

    public void addCookie(HttpServletResponse response, String cookieName, String value) {
        addCookie(response, cookieName, value, null);
    }

    public void addCookie(HttpServletResponse response, String cookieName, String value,
                          Integer maxAge) {
        Cookie cookie = new Cookie(cookieName, codec == null ? value : codec.encode(value));
        cookie.setPath(cookiePath);
        if (StringUtils.isNotBlank(cookieDomain)) {
            cookie.setDomain(cookieDomain);
        }
        if (null != maxAge) {
            cookie.setMaxAge(cookieMaxAge);
        }
        response.addCookie(cookie);
    }

    public void removeCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(Integer cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

}
