package com.youjh.service;

import com.writespring.*;

@Component("userService")
@Scope("prototype")
public class Userservice implements InitializingBean {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public void test(){

        System.out.println(orderService);
        System.out.println(beanName);
    }

    public void setBeanName(String name) {
        beanName = name;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化操作");
    }
}
