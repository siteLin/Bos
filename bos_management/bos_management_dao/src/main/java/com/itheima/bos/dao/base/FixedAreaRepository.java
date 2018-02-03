package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaServiceRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午10:01:55 <br/>       
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long> {


    /**
     * 根据定区id获取定区
     * @return
     */
    FixedArea findById(Long id);
}
  
