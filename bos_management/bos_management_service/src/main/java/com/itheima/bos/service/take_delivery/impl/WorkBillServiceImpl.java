package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.take_delivery.WorkBillRepository;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.WorkBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkBillServiceImpl implements WorkBillService {

    @Autowired
    private WorkBillRepository workBillRepository;

    @Override
    public void save(WorkBill workBill) {
        workBillRepository.save(workBill);
    }
}
