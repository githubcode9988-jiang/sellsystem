package com.imooc.dao;

import com.imooc.beans.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.math.BigDecimal;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired
    private OrderMasterDao repository;

    private final String OPENID = "123100";

    @Test
    public void  saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("师弟");
        orderMaster.setBuyerPhone("12345678912");
        orderMaster.setBuyerAddress("慕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(3.5));
        OrderMaster save = repository.save(orderMaster);
        Assert.assertNotNull(save);
    }


    @Test
    public void findByBuyerOpenid() {
        Pageable pageRequest = new PageRequest(0,1);
        Page<OrderMaster> result = repository.findAllBybuyerOpenid(OPENID, pageRequest);
        System.out.println(result.getTotalElements());
        //Assert.assertNotEquals(0,result.getTotalElements());
    }
}