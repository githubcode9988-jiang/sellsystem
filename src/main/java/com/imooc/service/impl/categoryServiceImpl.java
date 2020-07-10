package com.imooc.service.impl;

import com.imooc.beans.ProductCategory;
import com.imooc.dao.ProductCategoryDao;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class categoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryDao repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        ProductCategory byId = repository.findOne(categoryId);
        return byId;
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
