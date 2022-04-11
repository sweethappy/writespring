package com.youjh;

import com.writespring.ApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(Appconfig.class);
        Object userService = applicationContext.getBean("userService");
    }
}
