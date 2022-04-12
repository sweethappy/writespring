package com.youjh.service;

import com.writespring.Autowired;
import com.writespring.BeanNameAware;
import com.writespring.Component;
import com.writespring.Scope;

@Component("userService")
@Scope("prototype")
public class Userservice implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public void test(){

        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String name) {
        beanName = name;

    }
}
