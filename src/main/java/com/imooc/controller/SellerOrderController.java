package com.imooc.controller;

import com.imooc.dto.OrderDto;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer Page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map)    {

        PageRequest request = new PageRequest(Page-1,size);
        Page<OrderDto> orderDtoPage = orderService.findAllList(request);
        map.put("orderDtoPage",orderDtoPage);
        map.put("currentPage",Page);
        return  new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderID") String orderId,
                                Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findOne(orderId);
            orderService.cancel(orderDto);
        }catch (Exception e){
           map.put("msg",e.getMessage());
           map.put("url","/sell/seller/order/list");
           return new ModelAndView("common/error",map);
        }
        map.put("msg",SellExceptionEnums.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");

        return new ModelAndView("common/success");
    }


    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,
                                Map<String,Object> map){
            OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findOne(orderId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDto);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,
                                Map<String,Object> map){
        OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findOne(orderId);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg", SellExceptionEnums.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        orderService.finish(orderDto);
        return new ModelAndView("common/success", map);
    }



}
