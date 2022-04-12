package com.youjh;

import com.writespring.ApplicationContext;
import com.youjh.service.UserService;
import com.youjh.service.UserserviceImpl;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(Appconfig.class);
        UserService userService =(UserService) applicationContext.getBean("userService");
        userService.protest();  //1.代理对象
    }
}
