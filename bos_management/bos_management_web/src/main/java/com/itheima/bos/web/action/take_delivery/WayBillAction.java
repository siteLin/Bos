package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import com.itheima.bos.web.action.CommonAction;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class WayBillAction extends CommonAction<WayBill> {
    public WayBillAction() {
        super(WayBill.class);
    }

    @Autowired
    private WayBillService wayBillService;
    @Action(value = "wayBillAction_save")
    public String save() throws IOException {
        String msg = "1";
        try {
            wayBillService.save(getModel());
        } catch (Exception e) {
            e.printStackTrace();
            msg = "0";
        }
        // 将msg写给客户端
        write2Client(msg);
        return NONE;
    }
}
