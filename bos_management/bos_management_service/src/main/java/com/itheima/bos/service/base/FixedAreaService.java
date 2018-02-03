package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午9:59:15 <br/>       
 */
public interface FixedAreaService {

    /**  
     * save:储存fixedArea 
     *  
     * @param model  
     */
    void save(FixedArea fixedArea);

    /**  
     * findAll:.分页查询
     *  
     * @param pageable
     * @return  
     */
    Page<FixedArea> findAll(Pageable pageable);

    /**  
     * associationCourierToFixedArea: 关联Courier快递员到指定定区
     * @param id
     * @param courierId
     * @param takeTimeId  
     */
    void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId);

}
  
