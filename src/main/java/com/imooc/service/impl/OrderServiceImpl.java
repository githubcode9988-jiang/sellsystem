package com.imooc.service.impl;

import com.imooc.beans.OrderDetail;
import com.imooc.beans.OrderMaster;
import com.imooc.beans.ProductInfo;
import com.imooc.converter.OrderMaster2OrderDtoConverter;
import com.imooc.dao.OrderDetailDao;
import com.imooc.dao.OrderMasterDao;
import com.imooc.dto.OrderDto;
import com.imooc.dto.cartDto;
import com.imooc.enums.OrderStatusEnums;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.enums.payStatusEnums;
import com.imooc.execption.sellException;
import com.imooc.service.OrderService;
import com.imooc.service.ProductInterface;
import com.imooc.service.WebSocket;
import com.imooc.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInterface productInterface;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        List<cartDto> cartDtos = new ArrayList<>();

        //1.查询商品订单(数量，价格)
        for(OrderDetail orderDetail:orderDto.getOrderDetailList()){
            ProductInfo productIfo = productInterface.findOne(orderDetail.getProductId());
            if (null == productIfo){
                throw  new sellException(SellExceptionEnums.EXCEPTION_NOT_EXIST);
            }

            //2.计算订单总价
            orderAmount =productIfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setProductId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productIfo,orderDetail);
            orderDetailDao.save(orderDetail);

            cartDto cartdto = new cartDto(orderDetail.getProductId(),orderDetail.getProductQuantity());
            cartDtos.add(cartdto);

        }

        //3.写入订单数据库(orderMaster和orderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(payStatusEnums.WAIT.getCode());
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMasterDao.save(orderMaster);

        //4.扣库存
        productInterface.descresStock(cartDtos);

        //发生websocket消息
        webSocket.sendMessage("有新订单来了！");

        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {

        OrderMaster orderOne = orderMasterDao.findOne(orderId);
        if (orderOne == null){
            throw new sellException(SellExceptionEnums.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        if (orderDetailList == null){
            throw new sellException(SellExceptionEnums.ORDERDETAIL_NOT_EXIST);
        }

        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderOne,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> masterPage = orderMasterDao.findAllBybuyerOpenid(buyerOpenid, pageable);

        List<OrderDto> orderDtoList = OrderMaster2OrderDtoConverter.convert(masterPage.getContent());

        return new PageImpl<OrderDto>(orderDtoList,pageable,masterPage.getTotalElements());
    }

    @Override
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            throw new sellException(SellExceptionEnums.PRODUCT_STOCK_ERROR);
        }

        //取消订单
        orderDto.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster update = orderMasterDao.save(orderMaster);
        if (update == null){
            throw new sellException(SellExceptionEnums.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
           throw  new sellException(SellExceptionEnums.ORDERDETAIL_NOT_EXIST);
        }
        List<cartDto> carDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new cartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInterface.increaseStock(carDtoList);

        //如果已支付，返回金额
        if (orderDto.getPayStatus().equals(payStatusEnums.SUCCESS)){
            // TODO
        }
        return orderDto;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            throw new sellException(SellExceptionEnums.ORDER_STATUS_ERROR);
        }

        //设置订单状态
        orderDto.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if (result == null){
            throw new sellException(SellExceptionEnums.ORDER_UPDATE_FAIL);
        }

        return orderDto;
    }

    @Override
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            throw new sellException(SellExceptionEnums.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDto.getPayStatus().equals(payStatusEnums.WAIT.getCode())){
            throw new sellException(SellExceptionEnums.ORDER_PAY_STATUS_ERROT);
        }

        //修改支付状态
        orderDto.setPayStatus(payStatusEnums.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterDao.save(orderMaster);
        if (result == null){
            throw new sellException(SellExceptionEnums.ORDER_UPDATE_FAIL);
        }

        return orderDto;
    }

    @Override
    public Page<OrderDto> findAllList(Pageable pageable) {
        Page<OrderMaster> pageMaster = orderMasterDao.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMaster2OrderDtoConverter.convert(pageMaster.getContent());

        return new PageImpl<OrderDto>(orderDtoList,pageable,pageMaster.getTotalElements());

    }
}
