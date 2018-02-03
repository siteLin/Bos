package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }

    // 保存菜单
    @Override
    public void save(Menu menu) {
        if (menu.getParentMenu() == null) {
            menu.setParentMenu(null);
        }
        menuRepository.save(menu);
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {

        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findByUid(User user) {
        if ("admin".equals(user.getUsername())) {
            // 超级用户
            return menuRepository.findAll();
        } else {
            // 普通用户
            System.out.println(menuRepository.findMenuByUid(user.getId()).size());
            return menuRepository.findMenuByUid(user.getId());
        }
    }

}
