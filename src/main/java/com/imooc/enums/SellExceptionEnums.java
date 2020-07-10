package com.imooc.enums;

import lombok.Getter;

@Getter
public enum SellExceptionEnums {

    PARAM_ERROR(1,"参数错误"),
    EXCEPTION_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品错误"),
    ORDER_NOT_EXIST(12,"商品不存在"),
    ORDERDETAIL_NOT_EXIST(13,"商品详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态错误"),
    ORDER_UPDATE_FAIL(15,"订单取消失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROT(17,"订单状态不正确"),
    CART_EMPTY(18,"购物车为空"),
    ORDER_OWNER_ERROR(19,"订单号不匹配"),
    ORDER_CANCEL_SUCCESS(20,"订单取消成功"),
    PRODUCT_STATUS_ERROR(21,"商品状态不正确")
    ;

    private Integer code;

    private String message;

    SellExceptionEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
