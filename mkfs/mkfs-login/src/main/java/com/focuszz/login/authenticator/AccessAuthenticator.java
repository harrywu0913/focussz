package com.focuszz.login.authenticator;

public class AccessAuthenticator implements Authenticator {

    public boolean permit(String role, String url) {
        return false;
    }

}
