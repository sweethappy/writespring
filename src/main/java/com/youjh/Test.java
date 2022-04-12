package com.youjh;

import com.writespring.ApplicationContext;
import com.youjh.service.Userservice;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(Appconfig.class);
        Userservice userService =(Userservice) applicationContext.getBean("userService");
       userService.test();
    }
}
