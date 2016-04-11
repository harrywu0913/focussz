package com.focuszz.mkfs.authenticator.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.focuszz.mkfs.authenticator.definition.AuthenticationDefinition;

public class AuthenticationDefinitionRegistry implements BeanDefinitionRegistry {

    private final Map<String, AuthenticationDefinition> beanDefinitions        = new ConcurrentHashMap<String, AuthenticationDefinition>();

    private final Map<String, AuthenticationDefinition> patternBeanDefinitions = new ConcurrentHashMap<String, AuthenticationDefinition>();

    @Override
    public AuthenticationDefinition getBean(String beanName) {
        assetNotNull(beanName, "beanName");
        AuthenticationDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            for (String bName : patternBeanDefinitions.keySet()) {
                if (Pattern.matches(bName, beanName)) {
                    return patternBeanDefinitions.get(bName);
                }
            }
        }
        return beanDefinition;
    }

    @Override
    public void registerBean(String beanName, AuthenticationDefinition beanDefinition) {
        this.registerBean(beanName, beanDefinition, false);
    }

    @Override
    public void removeBean(String beanName) {
        this.removeBean(beanName, false);
    }

    @Override
    public void registerBean(String beanName, AuthenticationDefinition beanDefinition,
                             boolean pattern) {
        assetNotNull(beanName, "beanName");
        assetNotNull(beanDefinition, "beanDefinition");
        if (pattern) {
            patternBeanDefinitions.put(beanName, beanDefinition);
        } else {
            beanDefinitions.put(beanName, beanDefinition);
        }
    }

    @Override
    public void removeBean(String beanName, boolean pattern) {
        assetNotNull(beanName, "beanName");
        if (pattern) {
            patternBeanDefinitions.remove(beanName);
        } else {
            beanDefinitions.remove(beanName);
        }
    }

    private void assetNotNull(Object obj, String paramName) {
        if (null == obj)
            throw new NullPointerException(paramName + " can not be null.");
    }
}
