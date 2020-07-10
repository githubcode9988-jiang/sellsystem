package com.imooc.dao;

import com.imooc.beans.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {


   Page<OrderMaster> findAllBybuyerOpenid(String buyerOpenid, Pageable pageable);
}
