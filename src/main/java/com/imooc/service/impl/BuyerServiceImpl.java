package com.imooc.service.impl;

import com.imooc.dto.OrderDto;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.imooc.execption.sellException;

@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDto findOrderOne(String openid, String orderid) {
        OrderDto orderDto = checkOrderOwner(openid, orderid);
        return orderDto;
    }

    @Override
    public OrderDto cancel(String openid, String orderid) {
        OrderDto orderDto = checkOrderOwner(openid, orderid);
        if (orderDto == null){
            throw  new sellException(SellExceptionEnums.ORDER_UPDATE_FAIL);
        }
        return orderService.cancel(orderDto);
    }

    private OrderDto checkOrderOwner(String openid,String orderid){
        OrderDto orderDto = orderService.findOne(orderid);
        if ((orderDto == null)){
            return  null;
        }

        if (orderDto.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new sellException(SellExceptionEnums.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
