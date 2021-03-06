package com.yww.service.impl6;

import com.yww.dao.OrderDao;
import com.yww.dao.ProductDao;
import com.yww.entity.Order;
import com.yww.entity.Product;
import com.yww.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    //自动注入
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrder(Order order) {

        //设置一些基本的业务规则
        order.setCreateTime(new Date());
        order.setStatus("待付款");


        orderDao.insert(order);
        Product product = productDao.select(order.getProductsId());
        //修改库存
        product.setStock(product.getStock()-order.getNumber());
        //更新
        productDao.update(product);


    }
}
