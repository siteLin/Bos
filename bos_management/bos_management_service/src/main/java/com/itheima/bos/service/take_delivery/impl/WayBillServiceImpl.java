package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.take_delivery.WayBillRepository;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Override
    public void save(WayBill wayBill) {
        wayBillRepository.save(wayBill);
    }
}
