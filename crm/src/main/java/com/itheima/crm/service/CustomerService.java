package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.crm.domain.base.Customer;



/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年1月20日 下午4:27:27 <br/>       
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerService {

    /**  
     * findAll:获取所有客户
     *  
     * @return  
     */
    @GET
    @Path("/findAll")
    List<Customer> findAll();
    
    /**  
     * findCustomersUnAssociated:.获取未关联的Customer
     *  
     * @return  
     */
    @GET
    @Path("/findCustomersUnAssociated")
    List<Customer> findCustomersUnAssociated();
    
    /**  
     * findCustomersAssociatedByFixedArea:获取已关联的Customer
     *  
     * @return  
     */
    @GET
    @Path("/findCustomersAssociatedByFixedArea")
    List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId);

    /**  
     * fixedAreaAction_assignCustomers2FixedArea: 添加关联用户到定区
     *  
     * @param fixedAreaId
     * @param customerIds  
     */
    @PUT
    @Path("/fixedAreaAction_assignCustomers2FixedArea")
    void assignCustomers2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId,@QueryParam("customerIds") Long[] customerIds);
    
    /**  
     * regist:客户注册
     * @param customer  
     */
    @PUT
    @Path("/save")
    void save(Customer customer);
    
    /**  
     * findByTelephone: 根据telephone获取Customer对象
     *  
     * @param telephone
     * @return  
     */
    @GET
    @Path("/findByTelephone")
    Customer findByTelephone(@QueryParam("telephone") String telephone);
    
    /**  
     * active:. 激活
     *  
     * @param telephone  
     */
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone") String telephone);
    
    /**  
     * 通过telephone 和 password 获取Customer
     */
    @GET
    @Path("/findByTelephoneAndPassword")
    Customer findByTelephoneAndPassword(@QueryParam("telephone") String telephone, @QueryParam("password") String password);

    /**
     * 根据地址获取分区id
     * @param address
     * @return
     */
    @GET
    @Path("/findFixedAreaIdByAddress")
    String findFixedAreaIdByAddress(@QueryParam("address") String address);
}
  
