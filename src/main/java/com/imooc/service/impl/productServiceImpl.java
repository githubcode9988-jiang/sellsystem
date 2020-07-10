package com.imooc.service.impl;

import com.imooc.beans.ProductCategory;
import com.imooc.beans.ProductInfo;
import com.imooc.dao.ProductCategoryDao;
import com.imooc.dao.ProductInfoDao;
import com.imooc.dto.cartDto;
import com.imooc.enums.ProductStatusEnums;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.execption.sellException;
import com.imooc.service.ProductInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class productServiceImpl implements ProductInterface {

    @Autowired
    private ProductInfoDao repository;

    @Override
    public ProductInfo findOne(String productInfoId) {
        ProductInfo one = repository.findOne(productInfoId);
        return  one;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        List<ProductInfo> ProductUp = repository.findByProductStatus(ProductStatusEnums.UP.getCode());
        return ProductUp;
    }

    @Override
    public Page<ProductInfo> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        if (productInfo.getCreateTime() == null){
            productInfo.setCreateTime(new Date());
        }
        productInfo.setUpdateTime(new Date());
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<cartDto> cartDtoList) {
     for (cartDto cartDto:cartDtoList){
         ProductInfo productInfo = repository.findOne(cartDto.getProductId());
         if (productInfo == null){
             throw  new sellException(SellExceptionEnums.EXCEPTION_NOT_EXIST);
         }

         Integer count = productInfo.getProductStock() + cartDto.getProductQuantity();
         productInfo.setProductStock(count);
         repository.save(productInfo);
     }

    }

    @Override
    @Transactional
    public void descresStock(List<cartDto> cartDtoList) {
        for (cartDto cartDto:cartDtoList){
            ProductInfo productInfo = repository.findOne(cartDto.getProductId());
            if (productInfo == null){
                throw  new sellException(SellExceptionEnums.EXCEPTION_NOT_EXIST);
            }

            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();

            if (result<0){
                throw new sellException(SellExceptionEnums.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String ProductId) {

        ProductInfo productInfo = repository.findOne(ProductId);

        if (productInfo == null){
            throw  new sellException(SellExceptionEnums.ORDER_NOT_EXIST);
        }

        if (productInfo.getProductStatusEnums() == ProductStatusEnums.UP){
            throw  new sellException(SellExceptionEnums.PRODUCT_STATUS_ERROR);
        }

        //更改状态
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        return repository.save(productInfo);

    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findOne(productId);

        if (productInfo == null){
            throw  new sellException(SellExceptionEnums.ORDER_NOT_EXIST);
        }

        if (productInfo.getProductStatusEnums() == ProductStatusEnums.DOWN){
            throw  new sellException(SellExceptionEnums.PRODUCT_STATUS_ERROR);
        }

        //更改状态
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        return repository.save(productInfo);
    }
}
