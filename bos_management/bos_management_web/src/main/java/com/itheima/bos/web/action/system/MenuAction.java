package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import java.util.HashMap;
import java.util.List;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class MenuAction extends CommonAction<Menu> {

    @Autowired
    private MenuService menuService;


    public MenuAction() {
        super(Menu.class);
    }

    @Action(value = "menuAction_save", results = {
            @Result(name = "success", location = "/pages/system/menu.html", type = "redirect")
    })
    public String save() {

        Menu model = getModel();
        menuService.save(model);
        return SUCCESS;
    }
    @Action(value = "menuAction_findLevelOne")
    public String findLevelOne() throws IOException {
        // 获取父菜单
        List<Menu> list = menuService.findLevelOne();
        // 将list转成json并传给客户端
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu" });
        String json = list2Json(list, jsonConfig);
        write2Client(json);
        return NONE;
    }

    // 通过依赖注入获取page，rows
    private int page;
    private int rows;

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    @Action(value = "menuAction_pageQuery")
    public String pageQuery() throws IOException {

        HashMap<String, Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(Integer.parseInt(getModel().getPage())-1,rows);
        // 业务查询
        Page<Menu> page =  menuService.findAll(pageable);
        //将map转为json，并传给客户端
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu"});
        String json = page2Json(page,jsonConfig);
        write2Client(json);
        return null;
    }
    @Action(value = "menuAction_findByUid")
    public String findByUid() throws IOException {
        // 获取uid
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> menus = menuService.findByUid(user);
        // 将list转为json，并传给客户端
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles", "childrenMenus", "parentMenu","children"});
        String json = list2Json(menus, jsonConfig);
        write2Client(json);
        return NONE;
    }
}
