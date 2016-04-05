package com.focuszz.login.authenticator.config;

import java.util.Set;

public class AuthenticationDefinition {

    private boolean     login;

    private String      uri;

    private Set<String> roles;

    public AuthenticationDefinition() {
    }

    public AuthenticationDefinition(boolean need, Set<String> roles) {
        this(need, null, roles);
    }

    public AuthenticationDefinition(boolean login, String uri, Set<String> roles) {
        this.login = login;
        this.uri = uri;
        this.roles = roles;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
