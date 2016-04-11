package com.foucszz.login.authenticator.registry;

import com.focuszz.login.authenticator.config.AuthenticationDefinition;

public interface BeanDefinitionRegistry {

    public void registerBean(String beanName, AuthenticationDefinition beanDefinition);

    public void registerBean(String beanName, AuthenticationDefinition beanDefinition,
                             boolean pattern);

    public void removeBean(String beanName);

    public void removeBean(String beanName, boolean pattern);

    public AuthenticationDefinition getBean(String beanName);

}
