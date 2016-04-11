package com.focuszz.mkfs.web.form;

import java.util.LinkedHashMap;
import java.util.Map;

import com.focuszz.mkfs.common.utils.constant.Constants;
import com.focuszz.mkfs.entity.LoginInfo;
import com.focuszz.mkfs.entity.User;
import com.focuszz.mkfs.web.context.WebContext;
import com.focuszz.mkfs.web.context.WebContextWrapper;

public class BaseForm {

    private int                 id;

    private Map<String, String> errors = new LinkedHashMap<String, String>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void putError(String key, String val) {
        errors.put(key, "<label id=\"" + key + "-error\" class=\"error\" for=\"" + key + "\">" + val
                        + "</label>");
    }

    public final String getDomain() {
        return Constants.APPLICATION_DOMAIN;
    }

    public final String getStaticResourceDomain() {
        return Constants.STATIC_RESOURCES_DOMAIN;
    }

    public final String getRequestURI() {
        return getWebContext().getRequestURI();
    }

    public final boolean isLogon() {
        return getWebContext().isLogon();
    }

    public final LoginInfo getContextLoginInfo() {
        return getWebContext().getLoginInfo();
    }

    public final User getContextUser() {
        return getWebContext().getUser();
    }

    public final String getLoginAccout() {
        return getWebContext().getLoginAccount();
    }

    public final String getContextUserName() {
        return getWebContext().getUserName();
    }

    public final WebContext getWebContext() {
        WebContext wc = WebContextWrapper.get();
        return wc == null ? new WebContext() : wc;
    }
}
