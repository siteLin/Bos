package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午10:00:40 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    // 快递员信息的持久化对象
    @Autowired
    private CourierRepository courierRepository;
    // 派送时间信息的持久化对象
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    
    @Override
    public void save(FixedArea fixedArea) {
          
        fixedAreaRepository.save(fixedArea); 
    }
    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }
    @Override
    public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
          
        // 根据id获取定区信息
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        // 根据id获取快递员信息
        Courier courier = courierRepository.findOne(courierId);
        // 根据id获取派送时间信息
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        
        // fixedArea与courier关系 ManytoMany
        // courier与takeTime关系 ManytoOne
        // 由于courier放弃了对fixedArea的维护，因此只能fixedArea维护courier
        courier.setTakeTime(takeTime);
        fixedArea.getCouriers().add(courier);
        
    }

}
  
