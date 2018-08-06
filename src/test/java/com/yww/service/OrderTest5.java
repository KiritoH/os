package com.yww.service;


import com.yww.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//采取这种方式进行测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-service5.xml")
public class OrderTest5 {
    @Autowired
    private OrderService orderService;
    @Test
    public void testAddOrder(){
        Order order = new Order("100019","100002",2,1799,"","","");

        orderService.addOrder(order);

    }
}
