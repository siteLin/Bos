package com.itheima.bos.service.base;

import java.util.List;

import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月21日 下午4:31:45 <br/>       
 */
public interface TakeTimeService {

    /**  
     * findAll:查找所有的收派时间管理信息
     * @return 
     */
    List<TakeTime> findAll();

}
  
