package com.imooc.service.impl;

import com.imooc.beans.SellerInfo;
import com.imooc.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

      private static final String  openid="123123";

      @Autowired
      private SellerService sellerService;

      @Test
      public void find(){
          SellerInfo result = sellerService.findSellerInfoByOpenid(openid);
          Assert.assertNotNull(result);
      }
}
