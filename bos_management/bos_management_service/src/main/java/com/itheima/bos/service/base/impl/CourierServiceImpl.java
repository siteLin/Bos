package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午6:00:25 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {

    @Autowired
    private CourierRepository courierRepository;
    @Override 
    public void save(Courier model) {
          
        courierRepository.save(model);
    }
    @Override
    public Page<Courier> findAll(Pageable pageable) {
          
        Page<Courier> page = courierRepository.findAll(pageable);
        return page;
    }
    @Override
    @RequiresRoles("base")
    public void batchDelete(String ids) {
        String[] split = ids.split(","); 
        for (String id : split) {
            courierRepository.updateDeltagById(Long.parseLong(id),'1'); 
        }
    }
    @Override
    public void batchRestore(String ids) {
        String[] split = ids.split(","); 
        for (String id : split) {
            courierRepository.updateDeltagById(Long.parseLong(id),null); 
        }
    }
    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
          
        return courierRepository.findAll(specification, pageable);
    }
    @Override
    public List<Courier> findByDeltagIsNull() {
          
        return courierRepository.findByDeltagIsNull();
    }

}
  
