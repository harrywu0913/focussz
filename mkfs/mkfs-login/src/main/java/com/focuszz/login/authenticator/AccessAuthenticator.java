package com.focuszz.login.authenticator;

public class AccessAuthenticator implements Authenticator {

    private String configLocation;

    public AccessAuthenticator(String configLocation) {
        this.configLocation = configLocation;
    }

    public boolean permit(String role, String url) {
        return false;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }

}
