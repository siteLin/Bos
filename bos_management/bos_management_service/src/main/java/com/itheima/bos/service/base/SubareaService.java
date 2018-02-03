package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午5:38:33 <br/>       
 */
public interface SubareaService {

    /**  
     * save: 保存subArea
     *  
     * @param subArea  
     */
    void save(SubArea subArea);

    /**  
     * findAll:. 分页查询
     *  
     * @param pageable  
     * @return 
     */
    Page<SubArea> findAll(Pageable pageable);

    /**  
     * findByFixedAreaIsNull: 查询未与定区关联的分区信息
     *  
     * @return  
     */
    List<SubArea> findByFixedAreaIsNull();

    /**  
     * findByFixedArea:. 查询与指定定区关联的分区信息 
     *  
     * @param id
     * @return  
     */
    List<SubArea> findByFixedArea(Long id);

    /**  
     * assignSubArea2FixedArea:. 添加关联分区到定区
     *  
     * @param fixedAreaId
     * @param subAreaIds  
     */
    void assignSubArea2FixedArea(Long fixedAreaId, Long[] subAreaIds);

}
  
