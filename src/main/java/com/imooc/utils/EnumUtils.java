package com.imooc.utils;

import com.imooc.enums.CodeEnum;

public class EnumUtils {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> EnumClass){

        for (T each:EnumClass.getEnumConstants()){
             if (code.equals(each.getCode())){
                 return each;
             }
        }
        return  null;
    }
}
