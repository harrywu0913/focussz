
package com.focuszz.mkfs.web.context;

import com.focuszz.mkfs.entity.LoginInfo;
import com.focuszz.mkfs.entity.User;

public class WebContext {

    private String    sid;

    private String    ip;

    private boolean   logon;

    private String    requestURI;

    private LoginInfo loginInfo;

    private User      user;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isLogon() {
        return logon;
    }

    public void setLogon(boolean logon) {
        this.logon = logon;
    }

    public int getUserId() {
        return user == null ? 0 : user.getId();
    }

    public String getLoginAccount() {
        return loginInfo == null ? "" : loginInfo.getLoginAccount();
    }

    public String getUserName() {
        return user == null ? null : user.getUserName();
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return null == user ? null : user.getRole();
    }

}
