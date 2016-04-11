package com.focuszz.mkfs.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;

import com.focuszz.mkfs.common.utils.web.ApplicationContextWrapper;

public class InitApplicationListener extends ContextLoader implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ApplicationContextWrapper.setServletContext(sce.getServletContext());

        ApplicationContextWrapper.setApplicationContext(
            initWebApplicationContext(ApplicationContextWrapper.getServletContext()));

        ApplicationContextWrapper.setApplicationName(
            ApplicationContextWrapper.getServletContext().getInitParameter("applicationName"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        closeWebApplicationContext(sce.getServletContext());
    }

}
