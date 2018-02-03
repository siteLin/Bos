package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 上午8:27:53 <br/>       
 */
@Service
@Transactional
public class StandardServiceImpl implements StandardService {

    @Autowired
    private StandardRepository standardRepasitory;
    @Override
    public void save(Standard standard) {
        System.out.println(standard);
        standardRepasitory.save(standard);
        
    }
    @Override
    public Page<Standard> findAll(Pageable pageable) {
        Page<Standard> page = standardRepasitory.findAll(pageable);
        return page;
    }

}
  
