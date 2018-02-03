package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:RegionAction 分区
 * Function: <br/>
 * Date: 2018年1月16日 下午3:58:56 <br/>
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class AreaAction extends CommonAction<Area> {

    public AreaAction() {
        super(Area.class);
    }

    // 创建AreaService对象
    @Autowired
    private AreaService areaService;

    // 使用属性驱动获取前端页面传输的areaFile
    private File areaFile;

    public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }

    // 插入excel数据到数据库
    @Action(value = "areaAction_importXLS")
    public String importXLS() throws Exception {
        // System.out.println(areaFile.getAbsolutePath());
        // System.out.println(areaFile);
        // 获取excel工作簿对象
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(areaFile));
        // 获取工作表对象
        HSSFSheet sheet = workbook.getSheetAt(0);

        ArrayList<Area> list = new ArrayList<>();
        // 遍历工作表对象，获取row
        for (Row row : sheet) {
            // 去除表头行数据
            if (row.getRowNum() == 0) {
                continue;
            }
            Area area = new Area();
            // 遍历row获取单元格内容
            /*
             * for (Cell cell : row) { System.out.print(cell+" "); } System.out.println();
             */
            // 获取province 省
            String province = row.getCell(1).toString();
            province =  province.substring(0,province.length()-1);
            // 获取city 城市
            String city = row.getCell(2).toString();
            city =  city.substring(0,city.length()-1);
            // 获取district 区域
            String district = row.getCell(3).toString();
            district =  district.substring(0,district.length()-1);
            // 获取postcode 邮编
            String postcode = row.getCell(4).toString();
            // 使用PinYin4jUtils获取 citycode 城市编码，shortcode 简码
            // 切割city字符串
            String citycodeStr = city.substring(0, city.length() - 1);
            String citycode = PinYin4jUtils.hanziToPinyin(citycodeStr, "");
            String[] headByString = PinYin4jUtils.getHeadByString(province + city + district);
            String shortcode = PinYin4jUtils.stringArrayToString(headByString);

            // 将数据传到area中
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            area.setPostcode(postcode);
            area.setCitycode(citycode);
            area.setShortcode(shortcode);
            System.out.println(area);
            list.add(area);
        }
        areaService.save(list);
        workbook.close();
        return NONE;
    }

    // 分页查询区域信息arrea
    @Action(value = "areaAction_pageQuary")
    public String pageQuary() throws Exception {
        // System.out.println(page);
        // System.out.println(rows);
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Area> page = areaService.findAll(pageable);
        /*
         * // 获取总数据条数，和内容 long elements = page.getTotalElements(); List<Area> content =
         * page.getContent();
         * 
         * //将内容转为json数组 HashMap<String, Object> map = new HashMap<>(); map.put("total", elements);
         * map.put("rows", content); // 创建一个JSONConfig，过滤掉不不需要传给前端的数据 JsonConfig jsonConfig = new
         * JsonConfig(); jsonConfig.setExcludes(new String[] {"subareas"}); String json =
         * JSONObject.fromObject(map,jsonConfig).toString();
         * 
         * // 将json响应给客户端,并处理中文乱码 HttpServletResponse response = ServletActionContext.getResponse();
         * response.setContentType("application/json;charset=UTF-8");
         * response.getWriter().write(json);
         */

        // 为了提高服务器的性能，所有页面不需要的数据一律要忽略
        JsonConfig jsonConfig = new JsonConfig();
        // 指定在生成json数据的时候要忽略的字段
        jsonConfig.setExcludes(new String[] {"subareas"});
        // 将page转为json数组，并传给客户端
        String json = page2Json(page, jsonConfig);
        // json数组响应给客户端
        write2Client(json);
        return NONE;
    }
    
    // 通过属性驱动获取，下拉框搜索条件
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    // 查询所有区域信息arrea
    @Action(value = "areaAction_findAll")
    public String findAll() throws Exception {
        List<Area> list;
        if (StringUtils.isNotEmpty(q)) {
            System.out.println(q);
            list = areaService.findByQ(q);
            System.out.println(list);
        } else {
            
            Page<Area> page = areaService.findAll(null);
            list = page.getContent();
        }
        
        // 将list转为客户端需要json数组
        
        // 为了提高服务器的性能，所有页面不需要的数据一律要忽略
        JsonConfig jsonConfig = new JsonConfig();
        // 指定在生成json数据的时候要忽略的字段
        jsonConfig.setExcludes(new String[] {"subareas"});
        String json = list2Json(list,jsonConfig);
        // json数组响应给客户端
        write2Client(json);
        return NONE;
    }

}
