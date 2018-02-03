package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;
import com.itheima.bos.web.action.CommonAction;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class PromotionAction extends CommonAction<Promotion> {
    @Autowired
    private PromotionService promotionService;

    // 使用属性驱动获取上传的文件及文件名
    private File titleImgFile;
    private String titleImgFileFileName;
    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }

    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }

    public PromotionAction() {
        super(Promotion.class);
    }

    @Action(value = "promotionAction_save",
            results = {
                    @Result(name = "success",
                            location = "/pages/take_delivery/promotion.html",
                            type = "redirect"),
                    @Result(name = "error", location = "/error.html",
                            type = "redirect")})

    public String save() {
        Promotion promotion = getModel();
        try {
            if (titleImgFile != null) {
                // 获取upload文件的真实路径
                String saveDiredtory = "upload";
                String saveRealPath = ServletActionContext.getServletContext().getRealPath(saveDiredtory);
                // 获取文件名后缀，并使用随机数组成新的文件名
                String suffix = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
                String saveFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
                // 获取文件的存储路径，(包含文件名)
                String saveFileRealPath = saveRealPath + "/" +saveFileName;
                // 将文件存储到upload文件夹中（使用Apache FileUtils工具）
                File destFile = new File(saveFileRealPath);
                FileUtils.copyFile(titleImgFile, destFile);
                // 将文件的路径存储到Promotion bean对象中
                promotion.setTitleImg(ServletActionContext.getServletContext().getContextPath()+"/upload/"+saveFileName);
            }
            // 设置活动状态
            promotion.setStatus("1");
            // 保存成功
            promotionService.save(promotion);
            //System.out.println(promotion);

           return SUCCESS;

        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }

        //return NONE;
    }


    // 属性驱动获取page 和 rows
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

    @Action(value = "promotionAction_pageQuery")
    public String pageQuery() throws IOException {
        //System.out.println("page="+page);
        //System.out.println("rows="+rows);
        Page<Promotion> pages = promotionService.pageQuery(page-1,rows);
        // 将page转外json，并传个客户端
        String json = page2Json(pages,null);
        write2Client(json);
        return NONE;
    }

}
