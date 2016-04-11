package com.focuszz.mkfs.common.utils.web;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;

public class ApplicationContextWrapper {

    private static ApplicationContext applicationContext;

    private static ServletContext     servletContext;

    private static String             applicationName;

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ApplicationContextWrapper.applicationContext = applicationContext;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        ApplicationContextWrapper.servletContext = servletContext;
    }

    public static String getApplicationName() {
        return applicationName;
    }

    public static void setApplicationName(String applicationName) {
        ApplicationContextWrapper.applicationName = applicationName;
    }

}
