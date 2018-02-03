package com.itheima.crm.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.base.Customer;
import com.itheima.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年1月20日 下午4:33:24 <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public List<Customer> findAll() {
        
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersUnAssociated() {
        // System.out.println("findCustomersUnAssociated");
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {
       // System.out.println(fixedAreaId);
       // System.out.println(customerRepository.findByFixedAreaId(fixedAreaId));
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(String fixedAreaId, Long[] customerIds) {
        // 先根据fixedAreaId，解绑该定区id的所有关联项
        if (StringUtils.isNotEmpty(fixedAreaId)) {
            customerRepository.unbindByFixedAreaId(fixedAreaId);
        }
        
        if (customerIds != null && customerIds.length > 0 ) {
            
            for (Long id : customerIds) {
                // 修改Customer属性id值为参数id值的 fixedAreaId字段为参数fixedAreaId的值
                customerRepository.bindFixedAreaById(fixedAreaId,id);
            }
        }
        
    }

    @Override
    public void save(Customer customer) {
          
        customerRepository.save(customer);
    }

    @Override
    public Customer findByTelephone(String telephone) {
        
        return customerRepository.findByTelephone(telephone);  
    }

    @Override
    public void active(String telephone) {
          
        customerRepository.active(telephone);
        
    }

    // 通过telephone 和 password 获取Customer
    @Override
    public Customer findByTelephoneAndPassword(String telephone, String password) {
          
        return customerRepository.findByTelephoneAndPassword(telephone, password);
    }

    @Override
    public String findFixedAreaIdByAddress(String address) {
        return customerRepository.findFixedAreaIdByAddress(address);
    }


}
  
