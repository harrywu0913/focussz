package com.foucszz.login.authenticator.registry;

import com.focuszz.login.authenticator.config.AuthenticationDefinition;

public interface BeanDefinitionRegistry {

    public void registerBean(String beanName, AuthenticationDefinition beanDefinition);

    public void removeBean(String beanName);

    public AuthenticationDefinition getBean(String beanName);

}
