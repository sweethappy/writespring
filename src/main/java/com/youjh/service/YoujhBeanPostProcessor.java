package com.youjh.service;

import com.writespring.BeanPostProcessor;
import com.writespring.Component;

@Component
public class YoujhBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前");
        if(beanName.equals("userService")){
            ((Userservice)bean).setBeanName("进行Userservice初始化前的操作");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("进行初始化后的操作");
        return bean;
    }
}
