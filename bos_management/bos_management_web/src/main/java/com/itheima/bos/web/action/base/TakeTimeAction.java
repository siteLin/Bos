package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.CommonAction;

/**
 * ClassName:TakeTimeAction <br/>
 * Function: <br/>
 * Date: 2018年1月21日 下午4:27:41 <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class TakeTimeAction extends CommonAction<TakeTime> {

    public TakeTimeAction() {
        super(TakeTime.class);
    }

    // 获取TakeTimeService
    @Autowired
    private TakeTimeService takeTimeService;

    // 查找所有的收派时间管理信息
    @Action(value = "takeTimeAction_findAll")
    public String findAll() throws IOException {
        
        List<TakeTime> list = takeTimeService.findAll();
        
        // 将list转为json
        String json = list2Json(list, null);
        
        // 将json响应给客户端
        write2Client(json);
        return NONE;

    }

}
