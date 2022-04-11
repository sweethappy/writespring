package com.writespring;

public class ApplicationContext {

    private Class configClass;

    public ApplicationContext(Class configClass){
        this.configClass=configClass;
    }

    public Object getBean(String beanName){
        return null;
    }
}
