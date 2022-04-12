package com.youjh.service;

import com.writespring.BeanPostProcessor;
import com.writespring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component
public class YoujhBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        System.out.println("初始化前"+beanName);
        if(beanName.equals("userService")){
            ((UserserviceImpl)bean).setBeanName("进行Userservice初始化前的操作");
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        System.out.println("进行初始化后的操作"+beanName);
        if(beanName.equals("userService")){
            Object proxyInstance = Proxy.newProxyInstance(YoujhBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("代理逻辑");
                    return method.invoke(bean,args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
