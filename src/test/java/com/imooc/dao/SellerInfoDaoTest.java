package com.imooc.dao;

import com.imooc.beans.SellerInfo;
import com.imooc.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoDaoTest {

    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("weixin");
        sellerInfo.setPassword("weixin");
        sellerInfo.setOpenid("123123");
        SellerInfo save = sellerInfoDao.save(sellerInfo);
        Assert.assertNotNull(save);
    }


//    @Test
//    public void findByOpenid(){
//        SellerInfo byOpenid = sellerInfoDao.findByOpenid("123123");
//        Assert.assertNotNull(byOpenid);
//    }
}
