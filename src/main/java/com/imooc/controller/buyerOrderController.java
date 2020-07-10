package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.converter.OrderForm2OrderDtoConvert;
import com.imooc.dto.OrderDto;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.form.OrderForm;
import com.imooc.execption.sellException;
import com.imooc.service.BuyerService;
import com.imooc.service.OrderService;
import com.imooc.utils.ResultVOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Driver;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class buyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new sellException(SellExceptionEnums.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderForm2OrderDtoConvert.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            throw new sellException(SellExceptionEnums.CART_EMPTY);
        }

        OrderDto orderRsult = orderService.create(orderDto);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",orderRsult.getOrderId());

        return ResultVOUtils.success(map);

    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            throw new sellException(SellExceptionEnums.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDto> dtoPage = orderService.findList(openid, pageRequest);

        return ResultVOUtils.success(dtoPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDto> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderid){

        OrderDto orderDto = buyerService.findOrderOne(openid, orderid);

        return ResultVOUtils.success(orderDto);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDto> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderid){

        buyerService.cancel(openid, orderid);
        return ResultVOUtils.success();
    }

}
