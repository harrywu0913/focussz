package com.focuszz.login.authenticator;

public interface Authenticator {

    public boolean authenticate(String url, String role);

    public boolean authenticate(String url, boolean logon);

}
