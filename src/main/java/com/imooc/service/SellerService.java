package com.imooc.service;

import com.imooc.beans.SellerInfo;

public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
