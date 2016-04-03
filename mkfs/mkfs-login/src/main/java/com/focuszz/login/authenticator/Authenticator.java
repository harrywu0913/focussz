package com.focuszz.login.authenticator;

public interface Authenticator {

    public boolean permit(String role, String url);

}
