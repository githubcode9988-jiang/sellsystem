package com.imooc.controller;

import com.imooc.beans.ProductCategory;
import com.imooc.beans.ProductInfo;
import com.imooc.dto.OrderDto;
import com.imooc.enums.SellExceptionEnums;
import com.imooc.execption.sellException;
import com.imooc.form.ProductForm;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductInterface;
import com.imooc.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sun.awt.ModalExclude;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class ProductOrderController {

    @Autowired
    private ProductInterface productInterface;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer Page,
                             @RequestParam(value = "size",defaultValue = "5")Integer size,
                             Map<String,Object> map){

        PageRequest request = new PageRequest(Page-1,size);
        org.springframework.data.domain.Page<ProductInfo> infoPage = productInterface.findAll(request);
        map.put("infoPage",infoPage);
        map.put("currentPage",Page);
        return  new ModelAndView("product/list", map);
    }

    @GetMapping("/onsale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                                Map<String,Object> map){
        try {
            productInterface.onSale(productId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/offsale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productInterface.offSale(productId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }


    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                               Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInterface.findOne(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("product/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(form.getProductId())) {
                productInfo = productInterface.findOne(form.getProductId());
            }else {
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form, productInfo);
            productInterface.save(productInfo);
        }catch (sellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
        }
            map.put("url","/sell/seller/product/list");

        return  new ModelAndView("common/success",map);
    }
}
