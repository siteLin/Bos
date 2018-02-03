package com.itheima.bos.web.action.base;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ClassName:StandardAction 收派标准
 * Function: <br/>
 * Date: 2018年1月14日 下午9:22:57 <br/>
 */
@Controller
@Scope("prototype") // 相当于applicationContext.xml中，action中的scope属性，多例
@Namespace("/") // 相当于Struts中的package节点下的namespace属性
@ParentPackage("struts-default") // 相当于Struts中package接单下的extends属性
public class StandardAction extends CommonAction<Standard> {

    public StandardAction() {

        super(Standard.class);
        // TODO Auto-generated constructor stub

    }

    @Autowired
    private StandardService standardService;

    @Action(value = "standardAction_save", results = {
            @Result(name = "success", location = "/pages/base/standard.html", type = "redirect")}) // 保存Standard
    public String save() {

        standardService.save(getModel());
        // System.out.println("standardAction_save");
        // System.out.println(model);
        return SUCCESS;
    }

    @Action(value = "standardAction_pageQuery")
    // 分页查询Standard
    public String pageQuery() throws Exception {
        // System.out.println("page:"+page);
        // System.out.println("rows:"+rows);
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Standard> page = standardService.findAll(pageable);
        /*
         * // 获取总数据条数 long totalElements = page.getTotalElements(); // 获取rows,Standard集合
         * List<Standard> content = page.getContent(); // 封装total和rows到map集合中 HashMap<String,
         * Object> map = new HashMap<>(); map.put("total", totalElements); map.put("rows", content);
         * // 将map转为json格式 String json = JSONObject.fromObject(map).toString(); // 将结果返回给客户端
         * HttpServletResponse response = ServletActionContext.getResponse();
         * response.setContentType("application/json;charset=UTF-8");
         * response.getWriter().write(json);
         */

        // 将page转为json数组，并传给客户端
        String json = page2Json(page, null);
        // json数组响应给客户端
        write2Client(json);
        return NONE;
    }

    @Action(value = "standardAction_findAll")
    // 查询所有Standard
    public String findAll() throws Exception {
        Page<Standard> page = standardService.findAll(null);
        List<Standard> content = page.getContent();
        /*
         * // 将list转为json格式 String json = JSONArray.fromObject(content).toString(); // 将结果返回给客户端
         * HttpServletResponse response = ServletActionContext.getResponse();
         * response.setContentType("application/json;charset=UTF-8");
         * response.getWriter().write(json);
         */

        // 将list转为json格式
        String json = list2Json(content,null);
        // json数组响应给客户端
        write2Client(json);
        return NONE;
    }

}
