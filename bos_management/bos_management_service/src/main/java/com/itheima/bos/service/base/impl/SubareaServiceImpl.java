package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubareaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午5:41:10 <br/>       
 */
@Service
@Transactional
public class SubareaServiceImpl implements SubareaService {

    @Autowired
    private SubareaRepository subareaRepository;
    
    @Override
    public void save(SubArea subArea) {
        subareaRepository.save(subArea);  
    }

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
        return subareaRepository.findAll(pageable);
    }

    @Override
    public List<SubArea> findByFixedAreaIsNull() {
          
        return subareaRepository.findByFixedAreaIsNull();
    }

    @Override
    public List<SubArea> findByFixedArea(Long id) {
          
        return subareaRepository.findByFixedAreaId(id);
    }

    @Override
    public void assignSubArea2FixedArea(Long fixedAreaId, Long[] subAreaIds) {
          
     // 先根据fixedAreaId，解绑该定区id的所有关联项
        if (fixedAreaId != null) {
            subareaRepository.unbindByFixedAreaId(fixedAreaId);
        }
        
        if (subAreaIds != null && subAreaIds.length > 0 ) {
            
            for (Long id : subAreaIds) {
                // 修改Customer属性id值为参数id值的 fixedAreaId字段为参数fixedAreaId的值
                subareaRepository.bindFixedAreaById(fixedAreaId,id);
            }
        } 
        
    }

}
  
