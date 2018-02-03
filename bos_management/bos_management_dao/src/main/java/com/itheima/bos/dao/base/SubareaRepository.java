package com.itheima.bos.dao.base;

import java.util.List;

import javax.ws.rs.GET;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月18日 下午5:45:52 <br/>       
 */
public interface SubareaRepository extends JpaRepository<SubArea, Long> {

    /**  
     * findByFixedAreaIsNull: 查询定区（FixedArea）的值为null的所有分区（SubArea）信息
     *  
     * @return  
     */
    List<SubArea> findByFixedAreaIsNull();

    /**  
     * findByFixedArea:. 查询定区（FixedArea）的值为id的所有分区（SubArea）信息
     *  
     * @param id
     * @return  
     */
    //@Query("from SubArea s where fixedArea.id = ?1")
    List<SubArea> findByFixedAreaId(Long id);

    /**  
     * unbindByFixedAreaId: 当Customer属性fixedAreaId等于输入的fixedAreaId值时，将该fixedAreaId值制为null
     *  
     * @param fixedAreaId  
     */
    @Modifying
    @Query("update SubArea set fixedArea.id = null where fixedArea.id = ?1")
    void unbindByFixedAreaId(Long fixedAreaId);

    /**  
     * bindFixedAreaById: 修改Customer 属性id值为参数id值的 fixedAreaId字段为参数fixedAreaId的值
     *  
     * @param fixedAreaId
     * @param id  
     */
    @Modifying
    @Query("update SubArea set fixedArea.id = ?1 where id = ?2")
    void bindFixedAreaById(Long fixedAreaId, Long id);

}
  
