package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:subAreaAction 管理分区
 * Function:  <br/>  
 * Date:     2018年1月18日 下午5:23:04 <br/>       
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class SubAreaAction extends CommonAction<SubArea> {

    public SubAreaAction() {
        super(SubArea.class);  
    }
    
    @Autowired
    private SubareaService subareaService;
    
    @Action(value = "subAreaAction_save", results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect")} )
    public String save() {
        System.out.println(getModel());
       subareaService.save(getModel());
       return SUCCESS; 
    }
    
    // 分页查询
    @Action(value = "subAreaAction_pageQuery")
    public String pageQuery() throws IOException {
       Pageable pageable = new PageRequest(page-1,rows);
       Page<SubArea> page = subareaService.findAll(pageable);
       // 将page中的内容转换为json数组响应给客户端
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[] {"subareas","fixedArea"});
       String json = page2Json(page, jsonConfig);
       //System.out.println(json);
       // json数组响应给客户端
       write2Client(json);
       return NONE; 
    }
    
    // 查询未与定区关联的分区信息
    @Action(value = "subAreaAction_findSubAreasUnAssociated")
    public String findSubAreasUnAssociated() throws IOException {
       List<SubArea> list = subareaService.findByFixedAreaIsNull(); 
       // 将list中的内容转换为json数组响应给客户端
       JsonConfig jsonConfig = new JsonConfig();
       jsonConfig.setExcludes(new String[] {"subareas","couriers"});
       String json = list2Json(list, jsonConfig);
       // json数组响应给客户端
       write2Client(json);
       return NONE; 
    }
    
    //通过属性驱动获取fixedAreaId
    private Long fixedAreaId;
    public void setFixedAreaId(Long fixedAreaId) {
        this.fixedAreaId = fixedAreaId;
    }
    // 查询已经与定区关联的分区信息
    @Action(value = "subAreaAction_findSubAreasAssociated")
    public String findSubAreasAssociated() throws IOException {
        List<SubArea> list = subareaService.findByFixedArea(fixedAreaId); 
        // 将list中的内容转换为json数组响应给客户端
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas","couriers","fixedArea"});
        String json = list2Json(list, jsonConfig);
        // json数组响应给客户端
        write2Client(json);
        return NONE; 
    }
    
    // 使用属性驱动获取被选中的分区的id
    private Long[] subAreaIds;
    public void setSubAreaIds(Long[] subAreaIds) {
        this.subAreaIds = subAreaIds;
    }
    
    // 关联sub_Area分区到指定定区
    @Action(value = "subAreaAction_assignSubArea2FixedArea", results = {
         @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")   
    } )
    public String assignSubArea2FixedArea() throws IOException {
        //subareaService.
        /*System.out.println(fixedAreaId);
        for (Long long1 : subAreaIds) {
            System.out.println(long1);
        }*/
        subareaService.assignSubArea2FixedArea(fixedAreaId,subAreaIds);
        return SUCCESS;
    }
    
}
  
