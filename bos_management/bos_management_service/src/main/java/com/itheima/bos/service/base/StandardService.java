package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:standardService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 上午8:24:37 <br/>       
 */
public interface StandardService {

    void save(Standard model);

    
    /**  
     * Function 分页查询
     * findAll:. <br/>  
     *  
     * @param pageable
     * @return  
     */
    Page<Standard> findAll(Pageable pageable);

   
}
  
