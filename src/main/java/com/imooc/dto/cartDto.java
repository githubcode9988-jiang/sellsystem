package com.imooc.dto;

import lombok.Data;

@Data
public class cartDto {

    /** 商品id*/
    private String productId;

    /** 商品数量*/
    private Integer productQuantity;

    public cartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
