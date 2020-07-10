package com.imooc.service.impl;

import com.imooc.beans.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.enums.OrderStatusEnums;
import com.imooc.enums.payStatusEnums;
import com.imooc.service.OrderService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String BUYER_OPENID = "110110";

    private final String ORDER_ID ="1593493856471533101";

    @Test
    public void create() {

        OrderDto o1 = new OrderDto();
        o1.setBuyerName("熊大");
        o1.setBuyerOpenid(BUYER_OPENID);
        o1.setBuyerAddress("慕课网");
        o1.setBuyerPhone("12326345610");

        //购物车
        List<OrderDetail> cartList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123457");
        orderDetail.setProductQuantity(1);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("123456");
        orderDetail1.setProductQuantity(1);
        cartList.add(orderDetail);
        cartList.add(orderDetail1);



        o1.setOrderDetailList(cartList);

        OrderDto result = orderService.create(o1);
    }

    @Test
    public void findOne() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        Assert.assertEquals(ORDER_ID,orderDto.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDto> list = orderService.findList(BUYER_OPENID, pageRequest);
        Assert.assertNotEquals(0,list.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto cancel = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnums.CANCEL.getCode(),cancel.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto finish = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnums.FINISHED.getCode(),finish.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDto orderDto = orderService.findOne(ORDER_ID);
        OrderDto result = orderService.paid(orderDto);
        Assert.assertEquals(payStatusEnums.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void findAllList() {
        PageRequest pageRequest = new PageRequest(0,2);

        Page<OrderDto> orderDtoList = orderService.findAllList(pageRequest);

        Assert.assertTrue("查询订单详情的所有信息",orderDtoList.getTotalElements()>0);
    }
}
