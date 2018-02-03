package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class RoleAction extends CommonAction<Role> {

    @Autowired
    private RoleService roleService;

    public RoleAction() {
        super(Role.class);
    }

    @Action(value = "roleAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Role> page = roleService.findAll(pageable);
        // 将page转为json，并传给客户端
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions" , "menus"});
        String json = page2Json(page, jsonConfig);
        write2Client(json);
        return NONE;
    }

    // 通过属性驱动获取 menuIds, permissionIds
    private String menuIds;
    private Long[] permissionIds;

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }

    @Action(value = "roleAction_save")
    public String save() {
        roleService.save(getModel(), menuIds, permissionIds);
        return NONE;
    }

    @Action(value = "roleAction_findAll")
    public String findAll() throws IOException {
        Page<Role> page = roleService.findAll(null);
        // 将page转为json，并传给客户端
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions" , "menus"});
        String json = page2Json(page, jsonConfig);
        write2Client(json);
        return NONE;
    }

}
