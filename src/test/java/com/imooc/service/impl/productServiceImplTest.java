package com.imooc.service.impl;

import com.imooc.beans.ProductInfo;
import com.imooc.dao.ProductInfoDao;
import com.imooc.service.ProductInterface;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class productServiceImplTest {

    @Autowired
    private ProductInterface productInterface;

    @Test
    public void findOne() {
        ProductInfo byId = productInterface.findOne("123456");
        Assert.assertEquals("123456",byId.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productInterface.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void findAll() {
        PageRequest page = new PageRequest(0,2);
        Page<ProductInfo> all = productInterface.findAll(page);
        System.out.println(all.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123458");
        productInfo.setProductName("豆浆");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好喝的豆浆");
        productInfo.setProductIcon("htttp://******.com");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(3);

        ProductInfo save = productInterface.save(productInfo);
        Assert.assertNotNull(save);
    }


}