package com.yww.service.impl1;

import com.yww.dao.OrderDao;
import com.yww.dao.ProductDao;
import com.yww.entity.Order;
import com.yww.entity.Product;
import com.yww.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    //自动注入
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    //事务管理器对象
    @Autowired
    private PlatformTransactionManager transactionManager;
    //事务定义对象
    @Autowired
    private TransactionDefinition transactionDefinition;

    public void addOrder(Order order) {

        //设置一些基本的业务规则
        order.setCreateTime(new Date());
        order.setStatus("待付款");

        //A.开启一个事务,需要传入事务定义对象,会返回一个事务状态对象
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        try {
            orderDao.insert(order);
            Product product = productDao.select(order.getProductsId());
            //修改库存
            product.setStock(product.getStock()-order.getNumber());
            //更新
            productDao.update(product);
            //B.提交,所以在AB两者之间即单个事务
            transactionManager.commit(transactionStatus);
        } catch (TransactionException e) {
            //出现异常便回滚
            transactionManager.rollback(transactionStatus);
        }

    }
}
