package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.beans.OrderDetail;
import com.imooc.enums.OrderStatusEnums;
import com.imooc.enums.payStatusEnums;
import com.imooc.utils.EnumUtils;
import com.imooc.utils.serizlizer.Date2LongSerizlizer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //返回给前端的数据如果是null的话 不显示
public class OrderDto {

    /** 订单id*/
    private String orderId;

    /** 买家姓名*/
    private String buyerName;

    /** 买家手机号*/
    private String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家微信*/
    private String buyerOpenid;

    /** 订单总金额*/
    private BigDecimal orderAmount;

    /**  订单状态，默认为0新下单*/
    private Integer orderStatus ;


    /** 支付状态，默认为1未支付*/
    private Integer payStatus;


    /** 创建时间*/
    @JsonSerialize(using = Date2LongSerizlizer.class)
    private Date createTime;

    /** 更新时间*/
    @JsonSerialize(using = Date2LongSerizlizer.class)
    private Date updateTime;

    /** 多条订单详情*/
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnum() {
        return EnumUtils.getByCode(orderStatus,OrderStatusEnums.class);
    }

    @JsonIgnore
    public payStatusEnums getPayStatusEnum() {
        return EnumUtils.getByCode(payStatus,payStatusEnums.class);
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerOpenid() {
        return buyerOpenid;
    }

    public void setBuyerOpenid(String buyerOpenid) {
        this.buyerOpenid = buyerOpenid;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
