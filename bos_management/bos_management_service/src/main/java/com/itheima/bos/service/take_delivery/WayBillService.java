package com.itheima.bos.service.take_delivery;



import com.itheima.bos.domain.take_delivery.WayBill;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface WayBillService {

    @POST
    @Path("/save")
    void save(WayBill wayBill);
}
