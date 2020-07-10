package com.imooc.dao;

import com.imooc.beans.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao repository;

    @Test
    public  void saveTest(){
        OrderDetail orderdetail = new OrderDetail();
        orderdetail.setDetailId("123555");
        orderdetail.setOrderId("12320");
        orderdetail.setProductId("xzer11");
        orderdetail.setProductName("皮蛋粥");
        orderdetail.setProductPrice(new BigDecimal(3.2));
        orderdetail.setProductQuantity(50);
        orderdetail.setProductIcon("hettp//******");

        OrderDetail save = repository.save(orderdetail);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = repository.findByOrderId("12320");
        Assert.assertNotEquals(0,orderDetails.size());
    }
}