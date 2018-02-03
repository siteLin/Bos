package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
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

import java.io.IOException;


@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PermissionAction extends CommonAction<Permission> {

    @Autowired
    private PermissionService permissionService;

    public PermissionAction() {
        super(Permission.class);
    }

    // 保存
    @Action(value = "permissionAction_save",results = {
            @Result(name = "success", location = "/pages/system/permission.html",type = "redirect")
    })
    public String save() {

        permissionService.save(getModel());
        return SUCCESS;

    }

    // 分页查询
    @Action(value = "permissionAction_pageQuery")
    public String pageQuery() throws IOException {

        Pageable pageable = new PageRequest(page-1, rows);
        Page<Permission> page = permissionService.findAll(pageable);
        // 将page转为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        String json = page2Json(page, jsonConfig);
        write2Client(json);
        return NONE;
    }

    @Action(value = "permissionAction_findAll")
    public String findAll() throws IOException {

        Page<Permission> page = permissionService.findAll(null);
        // 将page转为json
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        String json = page2Json(page, jsonConfig);
        write2Client(json);
        return NONE;
    }
}
