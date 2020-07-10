package com.imooc.dao;

import com.imooc.beans.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoDao extends JpaRepository<SellerInfo,String> {

    public abstract SellerInfo findByOpenid(String openid);
}
