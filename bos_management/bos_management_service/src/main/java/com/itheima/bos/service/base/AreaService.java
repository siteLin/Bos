package com.itheima.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;

import javax.ws.rs.GET;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月17日 下午8:03:20 <br/>       
 */
public interface AreaService {

    /**  
     * save: 将list集合中的area保存到数据库
     */
    void save(ArrayList<Area> list);

    /**  
     * findAll: 分页查询Area
     * @return 
     */
    Page<Area> findAll(Pageable pageable);

    /**  
     * findByQ: 根据条件Q模糊查询，获取Area List集合
     *  
     * @param q
     * @return  
     */
    List<Area> findByQ(String q);


    /**
     * 通过省市区获取Area对象
     * @param province
     * @param city
     * @param district
     * @return
     */
    Area findByProvinceAndCityAndDistrict(String province, String city, String district);
}
  
