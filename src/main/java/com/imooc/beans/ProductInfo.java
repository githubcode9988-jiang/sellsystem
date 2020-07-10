package com.imooc.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.imooc.enums.ProductStatusEnums;
import com.imooc.utils.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    /**  单价*/
    private BigDecimal productPrice;

    /** 库存*/
    private Integer productStock;

    private String productDescription;

    private String productIcon;

    private Integer categoryType;

    private Integer productStatus;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnums getProductStatusEnums(){
        return EnumUtils.getByCode(productStatus,ProductStatusEnums.class);
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProductInfo{");
        sb.append("productId='").append(productId).append('\'');
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", productPrice=").append(productPrice);
        sb.append(", productStock=").append(productStock);
        sb.append(", productDescription='").append(productDescription).append('\'');
        sb.append(", productIcon='").append(productIcon).append('\'');
        sb.append(", categoryType=").append(categoryType);
        sb.append(", productStatus=").append(productStatus);
        sb.append('}');
        return sb.toString();
    }
}
