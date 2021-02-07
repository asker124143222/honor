package com.wood.honor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xu.dm
 * @Date: 2021/2/1 16:07
 * @Version: 1.0
 * @Description: TODO
 **/
@Component
public class ServiceB {

    @Autowired
    private ServiceA serviceA;

    public void sayHello() {
        System.out.println("hello serviceB, your hashcode is "+serviceA.hashCode());
    }
}
