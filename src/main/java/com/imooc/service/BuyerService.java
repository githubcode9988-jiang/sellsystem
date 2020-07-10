package com.imooc.service;


import com.imooc.dto.OrderDto;

public interface BuyerService {

    OrderDto findOrderOne(String openid,String orderid);

    OrderDto cancel(String openid,String orderid);
}
