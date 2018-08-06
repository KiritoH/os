package com.yww.service.impl2;

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
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    //自动注入
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    //注入事务模板对象
    @Autowired
    private TransactionTemplate transactionTemplate;


    public void addOrder(final Order order) {

        //设置一些基本的业务规则
        order.setCreateTime(new Date());
        order.setStatus("待付款");


        transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
                try {
                    //匿名内部类调用外部的变量,此变量必须为final修饰
                    orderDao.insert(order);
                    Product product = productDao.select(order.getProductsId());
                    //修改库存
                    product.setStock(product.getStock()-order.getNumber());
                    //更新
                    productDao.update(product);
                } catch (TransactionException e) {
                    //出现异常便回滚
                    transactionStatus.setRollbackOnly();
                }
                return null;
            }
        });



    }
}
