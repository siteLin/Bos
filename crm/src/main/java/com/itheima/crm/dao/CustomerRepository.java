package com.itheima.crm.dao;

import java.util.List;

import javax.ws.rs.GET;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.base.Customer;

/**
 * ClassName:CustomerRepository <br/>
 * Function: <br/>
 * Date: 2018年1月20日 下午4:40:39 <br/>
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * findByFixedAreaIdIsNull:查找FixedAreaId为null的Customer
     * 
     * @return
     */
    List<Customer> findByFixedAreaIdIsNull();

    /**
     * findByFixedAreaId:. 根据FixedAreaId查找Customer
     * 
     * @return
     */
    List<Customer> findByFixedAreaId(String fixedAreaId);

    /**
     * unbindByFixedAreaId: 当Customer属性fixedAreaId等于输入的fixedAreaId值时，将该fixedAreaId值制为null
     * 
     * @param fixedAreaId
     */
    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?1")
    void unbindByFixedAreaId(String fixedAreaId);

    /**
     * bindFixedAreaById: 修改Customer 属性id值为参数id值的 fixedAreaId字段为参数fixedAreaId的值
     * 
     * @param fixedAreaId
     * @param id
     */
    @Modifying
    @Query("update Customer set fixedAreaId = ?1 where id = ?2")
    void bindFixedAreaById(String fixedAreaId, Long id);

    /**
     * findByTelephone:根据telephone获取Customer对象
     * 
     * @param telephone
     * @return
     */
    Customer findByTelephone(String telephone);

    /**  
     * active:. 根据telephone更新customer中的Type
     *  
     * @param telephone  
     */
    @Modifying
    @Query("Update Customer set type=1 where telephone=?1")
    void active(String telephone);

    /**  
     * 通过telephone 和 password 获取Customer
     *  
     * @param telephone
     * @param password  
     * @return 
     */
    Customer findByTelephoneAndPassword(String telephone, String password);

    /**
     * 根据地址获取分区id
     * @param address
     * @return
     */
    @Query("select fixedAreaId from Customer where address = ?1")
    String findFixedAreaIdByAddress(String address);
}
