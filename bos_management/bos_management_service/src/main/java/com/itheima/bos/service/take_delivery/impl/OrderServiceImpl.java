package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.take_delivery.OrderRepository;
import com.itheima.bos.dao.take_delivery.WorkBillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.bos.domain.take_delivery.Order;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private WorkBillRepository workBillRepository;


    @Autowired
    private AreaService areaService;

    @Override
    public void save(Order order) {
        System.out.println(order);
        // 获取sendArea
        Area sendArea = order.getSendArea();
        if (sendArea != null) {
            String province = sendArea.getProvince();
            String city = sendArea.getCity();
            String district = sendArea.getDistrict();
            // 通过省市区获取sendArea的对象（因为原本的sendArea是没有id的）
            Area area = areaService.findByProvinceAndCityAndDistrict(province, city, district);
            order.setSendArea(area);
        }
        // 获取 recArea
        Area recArea = order.getRecArea();
        if (recArea != null) {
            String province = recArea.getProvince();
            String city = recArea.getCity();
            String district = recArea.getDistrict();
            // 通过省市区获取recArea的对象（因为原本的recArea是没有id的）
            Area area = areaService.findByProvinceAndCityAndDistrict(province, city, district);
            order.setRecArea(area);
        }
        orderRepository.save(order);

        // 进行分单操作
        // 1、通过sendAddress匹配customer表中的fixedAreaId；

        String sendAddress = order.getSendAddress();
        System.out.println(sendAddress);
        if(sendAddress != null) {
            String fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAddress")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .query("address",sendAddress)
                    .get(String.class);
            System.out.println("fixedAreaId="+fixedAreaId);
            // 通过定区Id获取定区
            if (StringUtils.isNotEmpty(fixedAreaId)) {
                FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
                // 获取该定区的快递员,根据快递员的上班时间还有配送能了获取
                Set<Courier> couriers = fixedArea.getCouriers();
                if (!couriers.isEmpty()) {
                    // 获取一个快递员，暂时不做根据快递员的上班时间还有配送能了获取
                    Iterator<Courier> iterator = couriers.iterator();
                    Courier courier = iterator.next();
                    // 设置工单
                    WorkBill workBill = new WorkBill();
                    workBill.setCourier(courier);
                    workBill.setAttachbilltimes(0);
                    workBill.setType("新");
                    workBill.setPickstate("新单");
                    workBill.setBuildtime(new Date());
                    workBill.setRemark(order.getRemark());
                    workBill.setSmsNumber("123");
                    workBill.setOrder(order);
                    workBillRepository.save(workBill);

                    // 发送短信给快递员
                    System.out.println("精准查询得到，有一个新的快递需要寄送，请前往取件！");
                    // 完成自动分单
                    System.out.println("完成制动分单");
                    return;

                }
            }
       }

       // 2、customer表中匹配不到fixAreaId,通过在去分区中，获取
        sendArea = order.getSendArea();
        if (sendArea != null) {
            System.out.println(sendAddress);
            //Oreder获取对应的分区
            Set<SubArea> subareas = sendArea.getSubareas();
           if (!subareas.isEmpty()) {
               // 遍历分区
               for (SubArea subArea : subareas) {
                   // 获取关键词
                   String keyWords = subArea.getKeyWords();
                   if (sendAddress.contains(keyWords)){
                       // 获取该分区对应的定区
                       FixedArea fixedArea = subArea.getFixedArea();
                       // 获取该定区的快递员,根据快递员的上班时间还有配送能了获取
                       Set<Courier> couriers = fixedArea.getCouriers();
                       if (!couriers.isEmpty()) {
                           // 获取一个快递员，暂时不做根据快递员的上班时间还有配送能了获取
                           Iterator<Courier> iterator = couriers.iterator();
                           Courier courier = iterator.next();
                           // 设置工单
                           WorkBill workBill = new WorkBill();
                           workBill.setCourier(courier);
                           workBill.setAttachbilltimes(0);
                           workBill.setType("新");
                           workBill.setPickstate("新单");
                           workBill.setBuildtime(new Date());
                           workBill.setRemark(order.getRemark());
                           workBill.setSmsNumber("123");
                           workBill.setOrder(order);
                           workBillRepository.save(workBill);

                           // 发送短信给快递员
                           System.out.println("模糊查询得到，有一个新的快递需要寄送，请前往取件！");
                           // 完成自动分单
                           System.out.println("完成制动分单");
                           return;
                       }

                   }
               }
           }

        }

        //3、以上两种方式都不能完成自动分单，那就只能人工分单
        System.out.println("实行人工分单");


    }
}
