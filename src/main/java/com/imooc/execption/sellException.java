package com.imooc.execption;

import com.imooc.enums.SellExceptionEnums;

public class sellException extends RuntimeException {

    private Integer code;

    public sellException(SellExceptionEnums sellExceptionEnums){
       super(sellExceptionEnums.getMessage());

       this.code = sellExceptionEnums.getCode();
    }

    public sellException(Integer code,String message){
       super(message);

       this.code = code;
    }

}
