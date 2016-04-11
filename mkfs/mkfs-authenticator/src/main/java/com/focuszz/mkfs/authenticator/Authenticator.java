package com.focuszz.mkfs.authenticator;

public interface Authenticator {

    public boolean authenticate(String url, String role);

    public boolean authenticate(String url, boolean logon);
}
