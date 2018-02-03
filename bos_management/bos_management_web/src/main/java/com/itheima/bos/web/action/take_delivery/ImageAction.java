package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.web.action.CommonAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class ImageAction extends CommonAction<Object> {

    // 通过属性驱动，获取去图片文件
    private File imgFile;
    // 通过属性驱动，获取去图片名字，struts规范
    private String imgFileFileName;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }

    public ImageAction() {
        super(Object.class);
    }

    @Action(value = "imageAction_upload")
    public String upload() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 使用Apache提供的FileUtils工具类，将文件存储到服务器中
            // 创建一个文件夹
            String saveDirPath = "upload";
            // 获取文件夹的真实路径
            String saveDirRealPath  = ServletActionContext.getServletContext().getRealPath(saveDirPath);
            // 获取文件名的后缀eg：.jsp
            String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            // 生成随机文件
            String fileName  = UUID.randomUUID().toString().replace("-", "") + suffix;

            // 保存的文件
            File destFile = new File(saveDirRealPath +"/"+ fileName);
            FileUtils.copyFile(imgFile,destFile);

            // 获取本项目的路径
            String contextPath =
                    ServletActionContext.getServletContext().getContextPath();
            // 返回的路径格式 : /bos_management_web/upload/xxx.jpg
            String relativePath = contextPath + "/" + saveDirPath + "/" + fileName;

            map.put("error",0);
            map.put("url",relativePath);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("message",e.getMessage());
        }
        // 将map转为json
        String json = map2Json(map, null);
        // 将map写给客户端
        write2Client(json);

        return NONE;
    }

}
