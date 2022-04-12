package com.youjh.service;

import com.writespring.Autowired;
import com.writespring.Component;
import com.writespring.Scope;

@Component("userService")
@Scope("prototype")
public class Userservice {

    @Autowired
    private OrderService orderService;

    public void test(){
        System.out.println(orderService);
    }
}
