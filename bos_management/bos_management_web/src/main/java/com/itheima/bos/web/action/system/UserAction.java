package com.itheima.bos.web.action.system;


import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class UserAction extends CommonAction<User> {

    @Autowired
    private UserService userService;

    // 通过属性驱动获取校验码
    private String checkCode;

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public UserAction() {
        super(User.class);
    }



    //后台登录
    @Action(value = "userAction_login",results = {
            @Result(name = "success",location = "index.html",type = "redirect"),
            @Result(name = "error",location = "login.html",type = "redirect")
    })
    public String login() {
        // 获取用户名，密码
        String username = getModel().getUsername();
        String password = getModel().getPassword();
       // 从session中获取验证码
        HttpSession session = ServletActionContext.getRequest().getSession();
        String serviceCode = (String) session.getAttribute("validateCode");

        // 判断验证码是否一致
        if (StringUtils.isNotEmpty(checkCode) && StringUtils.isNotEmpty(serviceCode) && checkCode.equals(serviceCode)) {
            try {
                // 验证码一致，执行用户名密码校验
                // 当前用户
                Subject subject =  SecurityUtils.getSubject();
                // 创建令牌
                AuthenticationToken token = new UsernamePasswordToken(username, password);
                // 用户登录
                subject.login(token);
                System.out.println("登录成功");
                return SUCCESS;
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码错误！");
            } catch ( UnknownAccountException e) {
                System.out.println("用户名出错");
            } catch (Exception e) {
                System.out.println("出错");
            }
        } else {
            //验证码不一致
            System.out.println("验证码不正确！");
        }
        return ERROR;
    }

    // 后台注销
    @Action(value = "userAction_logout", results = {
            @Result(name = "success",location = "/login.html",type = "redirect")
    })
    public String logout() {
        // 获取subject对象
        Subject subject = SecurityUtils.getSubject();
        // 设置退出登录
        subject.logout();
        // 移除session中的user对象
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("user");
        return SUCCESS;
    }

    // 属性驱动获取 roleIds
    private Long[] roleIds;

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    // 后台保存用户
    @Action(value = "userAction_save")
    public String save(){
        userService.save(getModel(), roleIds);
        return NONE;
    }


}
