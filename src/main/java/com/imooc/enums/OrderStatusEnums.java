package com.imooc.enums;

import lombok.Getter;

@Getter
public enum  OrderStatusEnums implements CodeEnum<Integer> {

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;

    private Integer code;

    private String message;

     OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return message;
    }
}
