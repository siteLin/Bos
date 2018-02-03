package com.itheima.bos.service.take_delivery;

import com.itheima.bos.domain.take_delivery.PageBean;
import com.itheima.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public interface PromotionService {

    /**
     * 保存promotion
     * @param promotion
     */

    void save(Promotion promotion);

    /**
     * 分页查询Promotion
     * @param page 页数
     * @param rows 每页显示行数
     */
    Page<Promotion> pageQuery(int page, int rows);

    /**
     * 提供给前端页面分页查询
     * @param page
     * @param size
     * @return
     */
    @GET
    @Path("/pageQuery")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    PageBean<Promotion> pageQuery4Fore(@QueryParam("page") int page,
                                       @QueryParam("size") int size);

}
