package com.imooc.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.beans.OrderDetail;
import com.imooc.dto.OrderDto;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.form.OrderForm;
import com.imooc.execption.sellException;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDtoConvert {

    public static OrderDto convert(OrderForm orderForm){

        Gson gson = new Gson();
        OrderDto orderDto = new OrderDto();

        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            throw new sellException(SellExceptionEnums.PARAM_ERROR);
        }
        orderDto.setOrderDetailList(orderDetailList);

        return orderDto;
    }
}
