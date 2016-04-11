
package com.focuszz.mkfs.web.context;

public class WebContextWrapper {

    private static final ThreadLocal<WebContext> WEB_CONTEXT_WRAPPER = new ThreadLocal<WebContext>();

    public WebContextWrapper() {
    }

    public static WebContext get() {
        return WEB_CONTEXT_WRAPPER.get();
    }

    public static void set(WebContext context) {
        WEB_CONTEXT_WRAPPER.set(context);
    }

    public static void remove() {
        WEB_CONTEXT_WRAPPER.set(null);
    }

}
