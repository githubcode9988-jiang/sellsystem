package com.imooc.dao;

import com.imooc.beans.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired
    private ProductCategoryDao repository;

    @Test
    public void findOne(){
        ProductCategory byId = repository.findOne(1);
        System.out.println(byId.toString());
    }

    @Test
    @Transactional
    public void save(){
       ProductCategory productCategory1 = new ProductCategory("男生最爱",4);
        ProductCategory product = repository.save(productCategory1);
        Assert.assertNotNull(product);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }
}