package com.imooc.service;


import com.imooc.beans.ProductCategory;
import com.imooc.beans.ProductInfo;
import com.imooc.dto.cartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface ProductInterface {

    ProductInfo findOne(String productInfoId);

    /**
     *   所有在架商品
     * @return
     */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(PageRequest pageRequest);

    ProductInfo save(ProductInfo productInfo);


    /**
     * 加库存
     */
     void increaseStock(List<cartDto> cartDtoList);

    /**
     * 减库存
     */

    void  descresStock(List<cartDto> cartDtoList);

    /**
     * 上架
     */
    ProductInfo onSale(String ProductId);

    /**
     * 下架
     */
    ProductInfo offSale(String productId);
}
