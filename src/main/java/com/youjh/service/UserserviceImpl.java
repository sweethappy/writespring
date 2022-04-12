package com.youjh.service;

import com.writespring.*;

@Component("userService")
@Scope("prototype")
public class UserserviceImpl implements InitializingBean, UserService {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public String getBeanName() {
        return beanName;
    }

    public void test(){
        System.out.println("在userService中实例化对象： "+ orderService);
        System.out.println("因为在初始化前我执行了postProcessBeforeInitialization，所以我的beanName被修改了"+beanName);
    }

    @Override
    public void protest() {
        System.out.println("看看我能不能执行");
    }

    public void setBeanName(String name) {
        beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化操作");
    }
}
