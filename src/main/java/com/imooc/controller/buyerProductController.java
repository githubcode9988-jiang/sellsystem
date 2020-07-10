package com.imooc.controller;

import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.beans.ProductCategory;
import com.imooc.beans.ProductInfo;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductInterface;
import com.imooc.utils.ResultVOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class buyerProductController {

    @Autowired
    private ProductInterface productInterface;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //查询所有上架商品
        List<ProductInfo> productUp = productInterface.findUpAll();

        //查询类目商品
        List<Integer> productTypeList = new ArrayList<Integer>();
        for (ProductInfo productInfo:productUp){
            productTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(productTypeList);

        //数据拼接
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory:categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productUp){
                if (productInfo.getCategoryType().equals(productVO.getCategoryType())){
                    ProductInfoVO productInfoVO1 = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO1);
                    productInfoVOList.add(productInfoVO1);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtils.success(productVOList);
    }

}
