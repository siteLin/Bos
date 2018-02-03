package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年1月15日 下午6:06:01 <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {


    /**  
     * updateDeltagById:根据Id修改Deltag(1:作废 ，0：正常)
     *  
     * @param parseLong
     * @param i  
     */
    @Modifying
    @Query("update Courier set deltag =?2 where id =?1")
    void updateDeltagById(long parseLong, Character i);

    /**  
     * findByDeltagIsNull:. 获取Deltag=Null,的所有快递员信息
     *  
     * @return  
     */
    List<Courier> findByDeltagIsNull();


}
  
