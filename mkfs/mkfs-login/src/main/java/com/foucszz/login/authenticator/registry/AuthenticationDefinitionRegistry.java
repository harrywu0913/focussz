package com.foucszz.login.authenticator.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.focuszz.login.authenticator.config.AuthenticationDefinition;

public class AuthenticationDefinitionRegistry implements BeanDefinitionRegistry {

    private Map<String, AuthenticationDefinition> beanDefinitions = new ConcurrentHashMap<String, AuthenticationDefinition>();

    @Override
    public void registerBean(String beanName, AuthenticationDefinition beanDefinition) {
        assetNotNull(beanName, "beanName");
        assetNotNull(beanDefinition, "beanDefinition");
        beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public void removeBean(String beanName) {
        assetNotNull(beanName, "beanName");
        beanDefinitions.remove(beanName);
    }

    @Override
    public AuthenticationDefinition getBean(String beanName) {
        assetNotNull(beanName, "beanName");
        return beanDefinitions.get(beanName);
    }

    private void assetNotNull(Object obj, String paramName) {
        if (null == obj)
            throw new NullPointerException(paramName + " can not be null.");
    }
}
