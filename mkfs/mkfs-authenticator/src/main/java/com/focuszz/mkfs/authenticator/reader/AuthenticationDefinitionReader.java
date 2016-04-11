package com.focuszz.mkfs.authenticator.reader;

import org.springframework.core.io.Resource;

import com.focuszz.mkfs.authenticator.registry.BeanDefinitionRegistry;

public interface AuthenticationDefinitionReader {

    public void readBeanDefinitions(Resource resource);

    public BeanDefinitionRegistry getRegistry();

    public void setRegistry(BeanDefinitionRegistry registry);
}
