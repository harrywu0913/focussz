package com.focuszz.mkfs.authenticator.definition;

import java.util.Set;

public class AuthenticationDefinition {

    private boolean     login;

    private String      uri;

    private Set<String> roles;

    private String      regexUri;

    private boolean     patternUri;

    public AuthenticationDefinition() {
    }

    public AuthenticationDefinition(boolean need, Set<String> roles) {
        this(need, null, roles);
    }

    public AuthenticationDefinition(boolean login, String uri, Set<String> roles) {
        this(login, uri, roles, false);
    }

    public AuthenticationDefinition(boolean login, String uri, Set<String> roles,
                                    boolean patternUri) {
        this.login = login;
        this.roles = roles;
        if (patternUri) {
            regexUri = uri;
        } else {
            this.uri = uri;
        }
        this.patternUri = patternUri;
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

    public String getRegexUri() {
        return regexUri;
    }

    public void setRegexUri(String regexUri) {
        this.regexUri = regexUri;
    }

    public boolean isPatternUri() {
        return patternUri;
    }

    public void setPatternUri(boolean patternUri) {
        this.patternUri = patternUri;
    }

}
