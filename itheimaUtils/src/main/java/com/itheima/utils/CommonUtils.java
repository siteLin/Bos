package com.itheima.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**  
 * ClassName:CommonUtils <br/>  
 * Function:  <br/>  
 * Date:     2018年1月22日 上午11:35:36 <br/>       
 */
public class CommonUtils {

    /**  
     * write2Client: 将结果返回给客户端
     * @throws IOException 
     *    
     */
    public static void write2Client(String result) throws IOException {
     // 将结果返回给客户端
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(result); 
    }
}
  
