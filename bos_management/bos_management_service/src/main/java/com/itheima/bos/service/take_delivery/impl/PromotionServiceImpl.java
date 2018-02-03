package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.take_delivery.PromotionRepository;
import com.itheima.bos.domain.take_delivery.PageBean;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    // 保存promotion
    @Override
    public void save(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    // 分页显示
    @Override
    public Page<Promotion> pageQuery(int page, int rows) {
        Pageable pageable = new PageRequest(page, rows);
        return promotionRepository.findAll(pageable);

    }

    // 提供给前提页面分页查询
    @Override
    public PageBean<Promotion> pageQuery4Fore(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<Promotion> pages = promotionRepository.findAll(pageable);
        // 将查询到的结果存放到pagebean中
        PageBean<Promotion> pageBean = new PageBean<>();
        pageBean.setRows(pages.getContent());
        pageBean.setTotal(pages.getTotalElements());
        return pageBean;
    }


}
