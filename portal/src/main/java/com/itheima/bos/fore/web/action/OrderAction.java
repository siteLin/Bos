package com.itheima.bos.fore.web.action;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;


@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order model;
    @Override
    public Order getModel() {
        model  = new Order();
        return model;
    }


    // 使用属性驱动获取 sendAreaInfo ，recAreaInfo
    private String sendAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    private String recAreaInfo;

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    // 发送验证信息
    @Action(value = "orderAction_add")
    public String add() {
        //System.out.println(sendAreaInfo);
        //System.out.println(recAreaInfo);
        //System.out.println(model);
        if (StringUtils.isNotEmpty(sendAreaInfo)) {
            String[] split = sendAreaInfo.split("/");
            String province = split[0].substring(0,split[0].length()-1);
            String city = split[1].substring(0,split[1].length()-1);
            String district = split[2].substring(0,split[2].length()-1);
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setSendArea(area);
        }
        if (StringUtils.isNotEmpty(recAreaInfo)) {
            String[] split = recAreaInfo.split("/");
            String province = split[0].substring(0,split[0].length()-1);
            String city = split[1].substring(0,split[1].length()-1);
            String district = split[2].substring(0,split[2].length()-1);
            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setRecArea(area);
        }
        WebClient//
                .create("http://localhost:8080/bos_management_web/webService/orderService/save")//
                .type(MediaType.APPLICATION_JSON)// 指定传输给对方的数据格式
                .post(model);


        return NONE;
    }

}
