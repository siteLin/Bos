package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CommonAction <br/>
 * Function: <br/>
 * Date: 2018年1月17日 下午9:47:22 <br/>
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {

    private T model;
    private Class<T> clazz;

    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;

    }

    @Override
    public T getModel() {
        if (model == null) {
            try {
                model = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // TODO Auto-generated method stub
        return model;
    }

    // 属性驱动，获取page，row
    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * page2json: 将page转为json数组，并传给客户端
     * 
     * @param page
     * @param jsonConfig
     * @return 
     * @throws IOException
     */
    public String page2Json(Page<T> page, JsonConfig jsonConfig) throws IOException {
        // 获取总数据条数
        long totalElements = page.getTotalElements();
        // 获取rows,Standard集合
        List<T> content = page.getContent();
        // 封装total和rows到map集合中
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", totalElements);
        map.put("rows", content);

        // 将map转为json格式
        String json;
        json = map2Json(map,jsonConfig);
        //System.out.println(json);
        /*// 将结果返回给客户端
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);*/
        return json;
    }

    public String map2Json(HashMap map, JsonConfig jsonConfig) {
        // 将map转为json格式
        String json;
        if (jsonConfig == null) {
            json = JSONObject.fromObject(map).toString();
        } else {
            json = JSONObject.fromObject(map, jsonConfig).toString();
        }
        return json;
    }

    /**
     * list2Json: 将list转换为json数组，并响应给客户端
     * 
     * @param list
     * @return 
     * @throws IOException
     */
    public String list2Json(List list, JsonConfig jsonConfig) throws IOException {
        // 将list转为json格式
        String json;
        if (jsonConfig == null) {
            json = JSONArray.fromObject(list).toString();
        } else {
            json = JSONArray.fromObject(list, jsonConfig).toString();
        }
        /*// 将结果返回给客户端
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);*/
        return json;
    }
    
    /**  
     * write2Client: 将结果返回给客户端
     * @throws IOException 
     *    
     */
    public void write2Client(String result) throws IOException {
     // 将结果返回给客户端
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result); 
    }

}
