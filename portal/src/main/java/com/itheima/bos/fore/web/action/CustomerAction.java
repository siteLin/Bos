package com.itheima.bos.fore.web.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.crm.domain.base.Customer;
import com.itheima.utils.CommonUtils;
import com.itheima.utils.MailUtils;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: 2018年1月21日 下午9:12:44 <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

    private Customer model;

    @Override
    public Customer getModel() {

        model = new Customer();
        return model;
    }

    // 发送验证信息
    @Action(value = "customerAction_sendSMS")
    public String sendSMS() {
        try {
            // 获取请求数据telephone
            String telephone = model.getTelephone();
            // 创建一个随机数的验证码
            String registCheckCode = RandomStringUtils.randomNumeric(6);
            // 将验证密码存储到session中
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("registCheckCode", registCheckCode);
            // 给手机号码发送验证码短信
            SmsUtils.sendSms(telephone, registCheckCode);
        } catch (ClientException e) {

            e.printStackTrace();

        }
        return NONE;
    }

    
    // 使用属性驱动获取注册界面客户填写的验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    // 客户注册
    @Action(value = "customerAction_regist",
            results = {
                    @Result(name = "success", location = "signup-success.html", type = "redirect"),
                    @Result(name = "error", location = "signup-fail.html", type = "redirect")})
    public String regist() {

        // 获取session中的的注册校验码
        String registCheckCode = (String) ServletActionContext.getRequest().getSession()
                .getAttribute("registCheckCode");
        
        // 判断验证码是否相同
        /*if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(registCheckCode)
                && checkcode.equals(registCheckCode))*///暂时关闭
        if(true) {
            WebClient.create("http://localhost:8180/crm/webService/customerService/save")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(model);
            String activeCode = RandomStringUtils.random(36);
            System.out.println(activeCode);
            
            // 将邮箱验证码存Redis中
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1, TimeUnit.DAYS);
            
            // 发送邮件
            String emailBody = "请点击<a href='http://localhost:8280/portal/customerAction_active.action?telephone="+model.getTelephone()+"&checkCode="+activeCode+"'>此链接</a>进行邮箱激活,激活码的有效期为24小时！";
            MailUtils.sendMail(model.getEmail(), "邮箱激活邮件", emailBody);
            return SUCCESS;
        }
        return ERROR;

    }
    
    // 使用属性驱动获取激活码checkCode
    private String checkCode;
    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
    // 账户激活
    @Action(value = "customerAction_active",
            results = {
                    @Result(name = "success", location = "login.html", type = "redirect"),
                    @Result(name = "actived", location = "login.html", type = "redirect"),
                    @Result(name = "error", location = "signup-fail.html", type = "redirect")})
    public String active() {
        
        String telephone = model.getTelephone();
        // 根据telephone获取redis中的 验证码activeCode
        String activeCode = redisTemplate.opsForValue().get(telephone);
        System.out.println("activeCode="+activeCode);
        System.out.println("checkCode="+checkCode);
        if (StringUtils.isNotEmpty(checkCode) && StringUtils.isNotEmpty(activeCode) && checkCode.equals(activeCode)) {
            // 通过telephone获取customer对象
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/findByTelephone")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .query("telephone", telephone)
                    .get(Customer.class);
            System.out.println(customer);
            if (customer != null && customer.getType() != null) {
                if (customer.getType() == 1) {
                    // 已激活
                    return "actived";
                } 
            }
            // 激活该customer
            WebClient.create("http://localhost:8180/crm/webService/customerService/active")
            .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .query("telephone", telephone)
            .put(null);
            return SUCCESS;
        }
        return ERROR;
        
    }

    // 检查该手机号码是否已经注册
    @Action(value = "customerAction_checkTelephone")
    public String checkTelephone() throws IOException {
        String isRegister = "0";
        String telephone = model.getTelephone();
        
        // 根据telephone获取Customer
        Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/findByTelephone")
                 .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                 .query("telephone", telephone)
                 .get(Customer.class);
        // 判断是否存在
        if (customer != null) {
            isRegister = "1"; 
        }
        // 将isRegister响应给客户端
        CommonUtils.write2Client(isRegister);
        
        return NONE;
        
    }
    
    // 登录
    @Action(value = "customerAction_login",
            results = {@Result(name = "success", location = "index.html", type = "redirect"),
                    @Result(name = "error", location = "login.html", type = "redirect")})
    public String login() {

        // 获取session中的validateCode
        HttpSession session = ServletActionContext.getRequest().getSession();
        String validateCode = (String) session.getAttribute("validateCode");
        String telephone = model.getTelephone();
        String password = model.getPassword();

        if (StringUtils.isNotEmpty(validateCode) && StringUtils.isNotEmpty(checkcode)
                && checkcode.equals(validateCode)) {
            // 验证码正确
            // 通过telephone 和 password 获取Customer
            Customer customer = WebClient
                    .create("http://localhost:8180/crm/webService/customerService/findByTelephoneAndPassword")
                    .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                    .query("telephone", telephone).query("password", password).get(Customer.class);
            System.out.println(customer);
            if (customer != null) {
                // 登录成功
                // 将customer存到session中
                session.setAttribute("customer", customer);
                return SUCCESS;
            }
        }
        return ERROR;
    }
    

}
