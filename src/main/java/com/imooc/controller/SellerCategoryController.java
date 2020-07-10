package com.imooc.controller;

import com.imooc.beans.ProductCategory;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.execption.sellException;
import com.imooc.form.CategoryForm;
import com.imooc.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                               Map<String,Object> map ){
        if (categoryId !=  null){
            ProductCategory categoryInfo = categoryService.findOne(categoryId);
            map.put("categoryInfo",categoryInfo);
        }
        return new ModelAndView("category/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (categoryForm.getCategoryId() != null){
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            if(productCategory.getCreateTime() == null) {
                productCategory.setCreateTime(new Date());
            }
            productCategory.setUpdateTime(new Date());
            categoryService.save(productCategory);
        }catch (sellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
            map.put("url","/sell/seller/category/list");

        return new ModelAndView("common/success",map);
    }
}
