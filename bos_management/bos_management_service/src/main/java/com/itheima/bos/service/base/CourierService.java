package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午5:57:22 <br/>       
 */
public interface CourierService {

    /**  
     * Function 保存快递员信息
     * save:. <br/>  
     *  
     * @param model  
     */
    void save(Courier model);

    /**  
     * Function 分页查询
     * findAll:. <br/>  
     *  
     * @param pageable
     * @return  
     */
    Page<Courier> findAll(Pageable pageable);

    /**  
     * Function 批量设置快递员信息失效
     * batchDelete:. <br/>  
     * @param ids  
     */
    void batchDelete(String ids);

    /**  
     * batchRestore:批量设置快递员信息有效
     * @param ids  
     */
    void batchRestore(String ids);

    /**  
     * findAll:根据查询条件，分页查找所有的快递员信息
     *  
     * @param specification
     * @param pageable
     * @return  
     */
    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);

    /**  
     * findByDeltagIsNull:获取所有有效快递员信息
     * @return  
     */
    List<Courier> findByDeltagIsNull();

}
  
