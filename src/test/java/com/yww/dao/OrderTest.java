package com.yww.dao;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderTest {
    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/os?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private String userName = "root";
    private String password = "123456";


    @Test
    public void addOrder(){
        //加载驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        //创建连接
        try {
            connection = DriverManager.getConnection(url,userName,password);

            //如果想要封装一下两个语句变成一个事务,设置AutoCommit为false
            connection.setAutoCommit(false);

            //创建语句
            Statement statement = connection.createStatement();
            //执行第一条
            statement.execute("insert into orders values('100001','100001',2,2499,now(),null ," +
                    "null,'东方未明','17573400608','成都','待发货')");
            //执行第二条
            statement.execute("update products set stock=stock-2 where id='100001'");
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            //如果发生异常,回滚
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }



}
