package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.openxmlformats.schemas.presentationml.x2006.main.CTCustomerDataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.crm.domain.base.Customer;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction 定期管理
 * Function:  <br/>  
 * Date:     2018年1月18日 下午9:42:21 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class FixedAreaAction extends CommonAction<FixedArea> {

    public FixedAreaAction() {
        super(FixedArea.class);  
    }
    
    @Autowired
    private FixedAreaService fixedAreaService;
    
    // 储存fixedArea
    @Action(value = "fixedAreaAction_save", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() {
        //System.out.println(getModel());
        fixedAreaService.save(getModel());
        return SUCCESS;
    }
    
    // 分页查询
    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> page = fixedAreaService.findAll(pageable);

        // 为了提高服务器的性能，所有页面不需要的数据一律要忽略
        JsonConfig jsonConfig = new JsonConfig();
        // 指定在生成json数据的时候要忽略的字段
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});
        
        // 将page转为json数组，并传给客户端
        String json = page2Json(page, jsonConfig);
        // json数组响应给客户端
        write2Client(json);
        return NONE;
    }
    
    // 获取未关联的Customer
    @Action(value = "fixedAreaAction_findCustomersUnAssociated")
    public String findCustomersUnAssociated() throws IOException {
        //System.out.println("findCustomersUnAssociated");
        List<Customer> list = (List<Customer>) WebClient
        .create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .getCollection(Customer.class);
        
        String json = list2Json(list, null);
        write2Client(json);
        
        return NONE;
    }
    // 获取已关联的Customer
    @Action(value = "fixedAreaAction_findCustomersAssociated")
    public String findCustomersAssociated() throws IOException {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomersAssociatedByFixedArea")
                .query("fixedAreaId", getModel().getId())
                .type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        
        String json = list2Json(list, null);
        write2Client(json);
        
        return NONE;
    }
    
    // 使用属性驱动获取被选中的客户的id
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    
    // 关联Customer客户到指定定区
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
         @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")   
    } )
    public String assignCustomers2FixedArea() throws IOException {
        WebClient
        .create("http://localhost:8180/crm/webService/customerService/fixedAreaAction_assignCustomers2FixedArea")
        .query("fixedAreaId", getModel().getId())
        .query("customerIds", customerIds)
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .put(null);
        
        return SUCCESS;
    }
    
    // 关联Courier快递员到指定定区
    // 属性驱动获取 courierId快递员id takeTimeId收派时间id
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")   
    } )
    public String associationCourierToFixedArea() throws IOException {
        /*System.out.println("FixedAreaID="+getModel().getId());
        System.out.println("courierId="+courierId);
        System.out.println("takeTimeId="+takeTimeId);*/
        fixedAreaService.associationCourierToFixedArea(getModel().getId(), courierId, takeTimeId);
        return SUCCESS;
    }
    

}
  
