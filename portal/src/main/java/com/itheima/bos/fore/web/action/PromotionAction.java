package com.itheima.bos.fore.web.action;

import com.itheima.bos.domain.take_delivery.PageBean;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.utils.CommonUtils;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONObject;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PromotionAction extends ActionSupport  {


    // 通过属性驱动，获取pageIndex，pageSize
    private int pageIndex;
    private int pageSize;

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Action(value = "promotionAction_pageQuery")
    public String pageQuery() throws IOException {

        PageBean<Promotion> pageBean = WebClient.create("http://localhost:8080/bos_management_web/webService/promotionService/pageQuery")
                .accept(MediaType.APPLICATION_JSON)
                .query("page",pageIndex)
                .query("size",pageSize)
                .get(PageBean.class);
        // 将结果转为json，并传给前台
        String json = JSONObject.fromObject(pageBean).toString();
        CommonUtils.write2Client(json);
        return NONE;
    }

}
