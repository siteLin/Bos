package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction 快递员信息
 * Function: <br/>
 * Date: 2018年1月15日 下午5:45:47 <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CourierAction extends CommonAction<Courier> {

    public CourierAction() {

        super(Courier.class);
        // TODO Auto-generated constructor stub

    }

    // 注入courierService
    @Autowired
    private CourierService courierService;

    @Action(value = "courierAction_save", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")}) // 保存快递员信息
    public String save() {
        courierService.save(getModel());
        return SUCCESS;

    }

    // 分页查询
    @Action(value = "courierAction_pageQuery")
    public String pageQuery() throws IOException {
        // 查询条件
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {

                // 获取搜索框的请求参数
                // System.out.println("model="+model);
                // 工号
                String courierNum = getModel().getCourierNum();
                // 取派员类型
                String type = getModel().getType();
                // 所属单位
                String company = getModel().getCompany();
                // 收派标准，获取用户上传的收派标准名字
                Standard standard = getModel().getStandard();

                ArrayList<Predicate> list = new ArrayList<>();
                // 如果快递员编号不为空,构造等值查询
                if (StringUtils.isNotEmpty(courierNum)) {
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                // 如果取派员类型不为空,构造等值查询
                if (StringUtils.isNotEmpty(type)) {
                    Predicate p2 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p2);
                }
                // 如果所属单位不为空,构造模糊查询
                if (StringUtils.isNotEmpty(company)) {
                    Predicate p3 =
                            cb.like(root.get("company").as(String.class), "%" + company + "%");
                    list.add(p3);
                }
                // 如果收派标准name不为空,构造模糊查询
                if (standard != null) {
                    String name = standard.getName();
                    if (StringUtils.isNotEmpty(name)) {
                        // 连表查询
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                    }
                }

                // 查询条件都为空的情况
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);

                return cb.and(arr);
            }

        };

        // page 从0开始
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Courier> page = courierService.findAll(specification, pageable);
        /*
         * // 获取总数据条数 long elements = page.getTotalElements(); // 获取page中的内容 List<Courier> content =
         * page.getContent(); // 封装中数据条数和page中的内容到map中 HashMap<String, Object> map = new
         * HashMap<>(); map.put("total", elements); map.put("rows", content);
         * 
         * // 为了提高服务器的性能，所有页面不需要的数据一律要忽略 JsonConfig jsonConfig = new JsonConfig(); //
         * 指定在生成json数据的时候要忽略的字段 jsonConfig.setExcludes(new String[] {"fixedAreas","takeTime"});
         * 
         * // 将map转为json格式的数据 String json = JSONObject.fromObject(map,jsonConfig).toString();
         * //System.out.println(json);
         * 
         * // 处理中文乱码，并将数据传给客户端 HttpServletResponse response = ServletActionContext.getResponse();
         * response.setContentType("application/json;charset=UTF-8");
         * response.getWriter().write(json);
         */

        // 为了提高服务器的性能，所有页面不需要的数据一律要忽略
        JsonConfig jsonConfig = new JsonConfig();
        // 指定在生成json数据的时候要忽略的字段
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});

        // 将page转为json数组，并传给客户端
        String json = page2Json(page, jsonConfig);
        // json数组响应给客户端
        write2Client(json);
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    // 批量设置快递员信息失效
    @Action(value = "courierAction_batchDelete", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")/*,
            @Result(name = "unauthorized", location = "/unauthorized.html", type = "redirect")*/})

    public String batchDelete() {
       /* try {*/
            // System.out.println(ids);
            courierService.batchDelete(ids);
            return SUCCESS;
       /* } catch (Exception e) {
            e.printStackTrace();
            System.out.println("您未拥有该权限");
            return "unauthorized";
        }*/


    }


    @Action(value = "courierAction_batchRestore", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    // 批量设置快递员信息有效
    public String batchRestore() {
        // System.out.println(ids);
        courierService.batchRestore(ids);
        return SUCCESS;

    }
    
    // 获取所有有效快递员信息
    @Action(value = "courierAction_listajax")
    public String listajax() throws IOException {
        List<Courier> list = courierService.findByDeltagIsNull();
        // 忽略页面不需要的数据
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas"});
        //将 list转为json
        String json = list2Json(list, jsonConfig);
        //将json字符串响应给客户端
        write2Client(json);
        return NONE;
        
    }
    
    
}
