package com.imooc.enums;

import lombok.Getter;

@Getter
public enum payStatusEnums implements CodeEnum<Integer> {
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付完成"),
    ;

    private Integer code;

    private String message;

    payStatusEnums(Integer code, String message) {
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
